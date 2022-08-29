package com.ajack.reactspringbootjpa.service;

import com.ajack.reactspringbootjpa.client.UserClient;
import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.model.api.UserApi;
import com.ajack.reactspringbootjpa.model.client.ClientPolicy;
import com.ajack.reactspringbootjpa.model.client.ClientUser;
import com.ajack.reactspringbootjpa.model.entity.AccountEntity;
import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import com.ajack.reactspringbootjpa.model.entity.UserEntity;
import com.ajack.reactspringbootjpa.repository.AccountRepository;
import com.ajack.reactspringbootjpa.repository.UserRepository;
import com.ajack.reactspringbootjpa.transform.OrganizationTransform;
import com.ajack.reactspringbootjpa.transform.UserTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
@Slf4j
public class UserService
{
    private final AccountRepository accountRepository;
    private final OrganizationService organizationService;
    private final SecurityContextService securityContextService;
    private final UserClient userClient;
    private final UserRepository userRepository;
    private final UserTransform userTransform;

    public UserService(
        AccountRepository accountRepository,
        OrganizationService organizationService,
        SecurityContextService securityContextService,
        UserClient userClient,
        UserRepository userRepository,
        UserTransform userTransform)
    {
        this.accountRepository = accountRepository;
        this.organizationService = organizationService;
        this.securityContextService = securityContextService;
        this.userClient = userClient;
        this.userRepository = userRepository;
        this.userTransform = userTransform;
    }

    public UserApi getUser()
    {
        final UserApi returnValue;

        log.debug("Enter UserService.getUser;");

        final String hid = securityContextService.getCurrentlyLoggedInUserHid();

        final Optional<UserEntity> storedUser = userRepository.findByHid(hid);

        if (storedUser.isPresent())
        {
            returnValue = userTransform.transformEntityToApi(storedUser.get());
        }
        else
        {
            final Optional<ClientUser> clientUser = userClient.getPolicyInformationForUser(hid);

            final List<String> eins = clientUser.get().getPolicies().stream()
                .map(ClientPolicy::getEin)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

            final List<AccountEntity> accounts = accountRepository
                .findDistinctByOrganizations_DeleteTimestampIsNullAndOrganizations_Organization_EinIn(eins);

            final AccountEntity account;

            if (accounts.size() < 1)
            {
                account = accountRepository.save(
                    AccountEntity.builder()
                        .name("test name")
                        .build());

                List<OrganizationApi> userOrganizations = clientUser.get().getPolicies().stream()
                    .map(clientPolicy -> clientPolicy.getEin().stream()
                        .map(ein -> OrganizationApi.builder()
                            .customerNumber(clientPolicy.getCustomerNumber())
                            .ein(ein)
                            .policyNumber(clientPolicy.getPolicyNumber())
                            .build())
                        .collect(Collectors.toList()))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

                //organizationService.createOrganization();

                ExampleMatcher organizationMatcher = ExampleMatcher.matching()
                    .withMatcher("customer_number", ignoreCase())
                    .withMatcher("ein", ignoreCase())
                    .withMatcher("policy_number", ignoreCase());

//                userOrganizations.forEach(userOrganization -> {
//                    Example<OrganizationEntity> orgExample = Example.of(org, organizationMatcher);
//                    boolean exists = userRepository.exists(orgExample);
//                });

                clientUser.get().getPolicies()
                    .forEach(clientPolicy -> clientPolicy.getEin()
                        .forEach(ein -> organizationService
                            .createOrganization(
                                account,
                                OrganizationApi.builder()
                                    .customerNumber(clientPolicy.getCustomerNumber())
                                    .ein(ein)
                                    .policyNumber(clientPolicy.getPolicyNumber())
                                    .build())));
            }
            else
            {
                account = accounts.get(0);
            }

            final UserEntity userToBeSaved = UserEntity.builder()
                .account(account)
                .firstName(clientUser.get().getFirstName())
                .hid(clientUser.get().getHid())
                .lastName(clientUser.get().getLastName())
                .build();

            final UserEntity savedUser = userRepository.save(userToBeSaved);

            returnValue = userTransform.transformEntityToApi(savedUser);
        }

        return returnValue;
    }
}
