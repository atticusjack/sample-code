package com.ajack.reactspringbootjpa.service;

import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.model.entity.AccountEntity;
import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import com.ajack.reactspringbootjpa.repository.OrganizationRepository;
import com.ajack.reactspringbootjpa.transform.OrganizationTransform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest
{
    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationTransform organizationTransform;

    private OrganizationService organizationService;

    @BeforeEach
    void beforeEach()
    {
        this.organizationService = new OrganizationService(
            organizationRepository,
            organizationTransform);
    }

    @Test
    void createOrganization_createsAnOrganization_andAssociatesItWithAnAccount()
    {
        final AccountEntity account = AccountEntity.builder()
            .id(UUID.randomUUID())
            .name("test organization account")
            .build();

        final String customerNumber = "test customer number";
        final String ein = "test ein";
        final String policyNumber = "test policy number";
        final OrganizationApi expectedOrganizationApi = OrganizationApi.builder()
            .customerNumber(customerNumber)
            .ein(ein)
            .policyNumber(policyNumber)
            .build();

        final OrganizationEntity expectedOrganizationEntity = OrganizationEntity.builder()
            .customerNumber(customerNumber)
            .ein(ein)
            .policyNumber(policyNumber)
            .build();

        when(organizationTransform.transformApiToEntity(expectedOrganizationApi))
            .thenReturn(expectedOrganizationEntity);

        organizationService.createOrganization(account, expectedOrganizationApi);

        final ArgumentCaptor<OrganizationEntity> organizationArgumentCaptor =
            ArgumentCaptor.forClass(OrganizationEntity.class);
        verify(organizationRepository).save(organizationArgumentCaptor.capture());

        final OrganizationEntity capturedOrganizationEntity = organizationArgumentCaptor.getValue();
        assertThat(capturedOrganizationEntity)
            .isEqualTo(expectedOrganizationEntity.toBuilder()
                .account(account)
                .build());
    }
}
