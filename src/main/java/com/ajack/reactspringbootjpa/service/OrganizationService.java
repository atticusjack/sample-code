package com.ajack.reactspringbootjpa.service;

import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.model.entity.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationService
{
    public OrganizationService()
    {}

    public void createOrganization(
        final AccountEntity account,
        final OrganizationApi organization)
    {
        log.debug("Enter OrganizationService.createOrganization; account: {}, organization: {}", account, organization);
    }
}
