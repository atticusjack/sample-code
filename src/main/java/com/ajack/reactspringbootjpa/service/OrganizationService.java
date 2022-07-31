package com.ajack.reactspringbootjpa.service;

import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.model.entity.AccountEntity;
import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import com.ajack.reactspringbootjpa.repository.OrganizationRepository;
import com.ajack.reactspringbootjpa.transform.OrganizationTransform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationService
{
    private final OrganizationRepository organizationRepository;
    private final OrganizationTransform organizationTransform;

    public OrganizationService(
        OrganizationRepository organizationRepository,
        OrganizationTransform organizationTransform)
    {
        this.organizationRepository = organizationRepository;
        this.organizationTransform = organizationTransform;
    }

    public void createOrganization(
        final AccountEntity account,
        final OrganizationApi organization)
    {
        log.debug("Enter OrganizationService.createOrganization; account: {}, organization: {}",
            account, organization);

        final OrganizationEntity organizationToSave = organizationTransform.transformApiToEntity(organization);
        organizationToSave.setAccount(account);

        organizationRepository.save(organizationToSave);
    }
}
