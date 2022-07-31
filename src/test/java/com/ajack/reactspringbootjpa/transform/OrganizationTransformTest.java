package com.ajack.reactspringbootjpa.transform;

import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class OrganizationTransformTest
{
    private OrganizationTransform organizationTransform;

    @BeforeEach
    void beforeEach()
    {
        this.organizationTransform = new OrganizationTransform();
    }

    @Test
    void transformApiToEntity_transformsApi_toEntity()
    {
        final String customerNumber = "customerNumber";
        final String ein = "ein";
        final String policyNumber = "policyNumber";

        final OrganizationApi organizationApi = OrganizationApi.builder()
            .customerNumber(customerNumber)
            .ein(ein)
            .policyNumber(policyNumber)
            .build();

        final OrganizationEntity organizationEntity = organizationTransform.transformApiToEntity(organizationApi);

        assertThat(organizationEntity).isEqualTo(OrganizationEntity.builder()
            .customerNumber(customerNumber)
            .ein(ein)
            .policyNumber(policyNumber)
            .build());
    }

    @Test
    void transformEntityToApi_transformsEntity_toApi()
    {
        final String customerNumber = "customer number";
        final String ein = "ein";
        final String policyNumber = "policy number";
        final OrganizationEntity organizationEntity = OrganizationEntity.builder()
            .customerNumber(customerNumber)
            .ein(ein)
            .policyNumber(policyNumber)
            .build();

        final OrganizationApi actualOrganizationApi = organizationTransform.transformEntityToApi(organizationEntity);

        assertThat(actualOrganizationApi).isEqualTo(OrganizationApi.builder()
            .customerNumber(customerNumber)
            .ein(ein)
            .policyNumber(policyNumber)
            .build());
    }
}
