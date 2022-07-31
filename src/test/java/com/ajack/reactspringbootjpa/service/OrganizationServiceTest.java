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

import java.util.List;
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

    @Mock
    private SecurityContextService securityContextService;

    private OrganizationService organizationService;

    @BeforeEach
    void beforeEach()
    {
        this.organizationService = new OrganizationService(
            organizationRepository,
            organizationTransform, securityContextService);
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

    @Test
    void getOrganizations_getsOrganizationsFromDB_byHid_whenOrganizationsAreInDB()
    {
        final String hid = "someHid";

        when(securityContextService.getCurrentlyLoggedInUserHid()).thenReturn(hid);

        final OrganizationEntity expectedOrganizationEntity = OrganizationEntity.builder()
            .customerNumber("customer")
            .ein("ein")
            .policyNumber("policy")
            .build();

        when(organizationRepository.findByAccount_Users_Hid(hid))
            .thenReturn(List.of(expectedOrganizationEntity));

        final OrganizationApi expectedOrganizationApi = OrganizationApi.builder()
            .customerNumber("c")
            .ein("e")
            .policyNumber("p")
            .build();

        when(organizationTransform.transformEntityToApi(expectedOrganizationEntity))
            .thenReturn(expectedOrganizationApi);

        final List<OrganizationApi> actualOrganizations = organizationService.getOrganizations();

        assertThat(actualOrganizations).isEqualTo(List.of(expectedOrganizationApi));
    }
}
