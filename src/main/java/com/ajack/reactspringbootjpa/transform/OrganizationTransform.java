package com.ajack.reactspringbootjpa.transform;

import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrganizationTransform
{
    public OrganizationTransform() {}

    public OrganizationEntity transformApiToEntity(
        final OrganizationApi organizationApi)
    {
        log.debug("Enter OrganizationTransform.transformApiToEntity; organizationApi: {}", organizationApi);

        return OrganizationEntity.builder()
            .customerNumber(organizationApi.getCustomerNumber())
            .ein(organizationApi.getEin())
            .policyNumber(organizationApi.getPolicyNumber())
            .build();
    }

    public OrganizationApi transformEntityToApi(
        final OrganizationEntity organizationEntity)
    {
        log.debug("Enter OrganizationTransform.transformEntityToApi; organizationEntity: {}", organizationEntity);

        return null;
    }
}
