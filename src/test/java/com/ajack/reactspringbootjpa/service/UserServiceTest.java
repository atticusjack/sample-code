package com.ajack.reactspringbootjpa.service;

import com.ajack.reactspringbootjpa.client.UserClient;
import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.model.api.UserApi;
import com.ajack.reactspringbootjpa.model.client.ClientPolicy;
import com.ajack.reactspringbootjpa.model.client.ClientUser;
import com.ajack.reactspringbootjpa.model.entity.AccountEntity;
import com.ajack.reactspringbootjpa.model.entity.UserEntity;
import com.ajack.reactspringbootjpa.repository.AccountRepository;
import com.ajack.reactspringbootjpa.repository.UserRepository;
import com.ajack.reactspringbootjpa.transform.UserTransform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private SecurityContextService securityContextService;

    @Mock
    private UserClient userClient;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTransform userTransform;

    private UserService userService;

    @BeforeEach
    void beforeEach()
    {
        userService = new UserService(
            accountRepository, organizationService, securityContextService,
            userClient,
            userRepository,
            userTransform);
    }

    @Test
    void getUser_getsUserFromDB_ifUserExistsInDB()
    {
        final String expectedHid = "123456";

        when(securityContextService.getCurrentlyLoggedInUserHid()).thenReturn(expectedHid);

        final UserEntity expectedUserEntity = UserEntity.builder()
            .firstName("first_name")
            .hid(expectedHid)
            .lastName("last_name")
            .build();

        when(userRepository.findByHid(expectedHid)).thenReturn(Optional.of(expectedUserEntity));

        final UserApi expectedUser = UserApi.builder()
            .firstName("first")
            .id(UUID.randomUUID())
            .lastName("last")
            .build();

        when(userTransform.transformEntityToApi(expectedUserEntity)).thenReturn(expectedUser);

        final UserApi actualUser = userService.getUser();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void getUser_createsUser_ifUserNotInDB_andAssociatesUserWithAccount_ifAccountExistsForEins()
    {
        final String expectedHid = "123456";

        when(securityContextService.getCurrentlyLoggedInUserHid()).thenReturn(expectedHid);

        when(userRepository.findByHid(expectedHid)).thenReturn(Optional.empty());

        final String firstName = "first";
        final String lastName = "last";
        final Optional<ClientUser> expectedClientUser = Optional.of(ClientUser.builder()
            .firstName(firstName)
            .hid(expectedHid)
            .lastName(lastName)
            .policies(List.of(
                ClientPolicy.builder()
                    .customerNumber("customer 1")
                    .ein(List.of(
                        "ein 1",
                        "ein 2"))
                    .policyNumber("policy 1")
                    .build(),
                ClientPolicy.builder()
                    .customerNumber("customer 2")
                    .ein(List.of(
                        "ein 3"))
                    .policyNumber("policy 2")
                    .build()))
            .build());

        when(userClient.getPolicyInformationForUser(expectedHid)).thenReturn(expectedClientUser);

        final AccountEntity expectedAccount = AccountEntity.builder()
            .name("test account")
            .id(UUID.randomUUID())
            .build();

        //when(accountRepository.findDistinctByOrganizations_EinIsIn(List.of("ein 1", "ein 2", "ein 3")))
        //    .thenReturn(List.of(expectedAccount));

        final UserEntity savedUserEntity = UserEntity.builder()
            .account(expectedAccount)
            .firstName(firstName)
            .hid(expectedHid)
            .lastName(lastName)
            .build();

        final UUID userId = UUID.randomUUID();
        final UserEntity expectedUserEntity = savedUserEntity.toBuilder()
            .id(userId)
            .build();

        when(userRepository.save(savedUserEntity)).thenReturn(expectedUserEntity);

        final UserApi expectedUser = UserApi.builder()
            .id(UUID.randomUUID())
            .firstName("f")
            .lastName("l")
            .build();

        when(userTransform.transformEntityToApi(expectedUserEntity)).thenReturn(expectedUser);

        final UserApi actualUser = userService.getUser();

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void getUser_callsUserClient_andCreatesUser_ifUserNotInDB()
    {
        final String expectedHid = "123456";

        when(securityContextService.getCurrentlyLoggedInUserHid()).thenReturn(expectedHid);

        when(userRepository.findByHid(expectedHid)).thenReturn(Optional.empty());

        final String firstName = "first";
        final String lastName = "last";
        final Optional<ClientUser> expectedClientUser = Optional.of(ClientUser.builder()
            .firstName(firstName)
            .hid(expectedHid)
            .lastName(lastName)
            .policies(List.of(
                ClientPolicy.builder()
                    .customerNumber("customer 1")
                    .ein(List.of(
                        "ein 1",
                        "ein 2"))
                    .policyNumber("policy 1")
                    .build(),
                ClientPolicy.builder()
                    .customerNumber("customer 2")
                    .ein(List.of(
                        "ein 3"))
                    .policyNumber("policy 2")
                    .build()))
            .build());

        when(userClient.getPolicyInformationForUser(expectedHid)).thenReturn(expectedClientUser);

        //when(accountRepository.findDistinctByOrganizations_EinIsIn(List.of("ein 1", "ein 2", "ein 3")))
        //    .thenReturn(List.of());

        final AccountEntity accountToBeSaved = AccountEntity.builder()
            .name("test name")
            .build();

        final AccountEntity expectedAccount = accountToBeSaved.toBuilder()
            .id(UUID.randomUUID())
            .build();

        when(accountRepository.save(accountToBeSaved)).thenReturn(expectedAccount);

        final ArgumentCaptor<OrganizationApi> organizationCaptor = ArgumentCaptor.forClass(OrganizationApi.class);
        doNothing().when(organizationService).createOrganization(eq(expectedAccount), organizationCaptor.capture());

        final UserEntity savedUserEntity = UserEntity.builder()
            .account(expectedAccount)
            .firstName(firstName)
            .hid(expectedHid)
            .lastName(lastName)
            .build();

        final UUID userId = UUID.randomUUID();
        final UserEntity expectedUserEntity = savedUserEntity.toBuilder()
            .id(userId)
            .build();

        when(userRepository.save(savedUserEntity)).thenReturn(expectedUserEntity);

        final UserApi expectedUser = UserApi.builder()
            .id(UUID.randomUUID())
            .firstName("f")
            .lastName("l")
            .build();

        when(userTransform.transformEntityToApi(expectedUserEntity)).thenReturn(expectedUser);

        final UserApi actualUser = userService.getUser();
        assertThat(actualUser).isEqualTo(expectedUser);

        final List<OrganizationApi> capturedOrganizations = organizationCaptor.getAllValues();
        assertThat(capturedOrganizations).containsExactlyInAnyOrder(
            OrganizationApi.builder()
                .customerNumber("customer 1")
                .ein("ein 1")
                .policyNumber("policy 1")
                .build(),
            OrganizationApi.builder()
                .customerNumber("customer 1")
                .ein("ein 2")
                .policyNumber("policy 1")
                .build(),
            OrganizationApi.builder()
                .customerNumber("customer 2")
                .ein("ein 3")
                .policyNumber("policy 2")
                .build());
    }
}
