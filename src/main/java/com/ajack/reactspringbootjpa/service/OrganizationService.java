package com.ajack.reactspringbootjpa.service;

import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.model.entity.AccountEntity;
import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import com.ajack.reactspringbootjpa.repository.OrganizationRepository;
import com.ajack.reactspringbootjpa.transform.OrganizationTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrganizationService
{
    private final OrganizationRepository organizationRepository;
    private final OrganizationTransform organizationTransform;
    private final SecurityContextService securityContextService;

    public OrganizationService(
        OrganizationRepository organizationRepository,
        OrganizationTransform organizationTransform,
        SecurityContextService securityContextService)
    {
        this.organizationRepository = organizationRepository;
        this.organizationTransform = organizationTransform;
        this.securityContextService = securityContextService;
    }

    public void createOrganization(
        final AccountEntity account,
        final OrganizationApi organization)
    {
        log.debug("Enter OrganizationService.createOrganization; account: {}, organization: {}",
            account, organization);

        final OrganizationEntity organizationToSave = organizationTransform.transformApiToEntity(organization);
        //organizationToSave.setAccounts(Set.of(account));

        organizationRepository.save(organizationToSave);
    }

    public List<OrganizationApi> getOrganizations()
    {
        log.debug("Enter OrganizationService.getOrganizations");

        final String hid = securityContextService.getCurrentlyLoggedInUserHid();

        return null;
//        return organizationRepository.findByAccounts_Users_Hid(hid).stream()
//            .map(organizationTransform::transformEntityToApi)
//            .collect(Collectors.toList());
    }
}
