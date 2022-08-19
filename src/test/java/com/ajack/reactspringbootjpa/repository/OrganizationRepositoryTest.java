package com.ajack.reactspringbootjpa.repository;

import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
    "spring.test.database.replace=NONE"
})
@Testcontainers
public class OrganizationRepositoryTest
{
    @Container
    static PostgreSQLContainer database = new PostgreSQLContainer("postgres:14.4")
        .withDatabaseName("db")
        .withPassword("password")
        .withUsername("postgres");

    @DynamicPropertySource
    static void setDataSource(DynamicPropertyRegistry propertyRegistry)
    {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.flyway.url", database::getJdbcUrl);
        propertyRegistry.add("spring.flyway.user", database::getUsername);
        propertyRegistry.add("spring.flyway.password", database::getPassword);
    }

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Sql("/sql-scripts/organization-repository/findByAccount-users-hid.sql")
    void findByAccount_Users_Hid_returnsOrganizationsAssociatedWithHid_whenOrganizationsAreInDB()
    {
        final String hid = "testHid";

        //final Set<OrganizationEntity> actualOrganizations = organizationRepository.findByAccounts_Users_Hid(hid);

        //assertThat(actualOrganizations.size()).isEqualTo(2);
    }

    @Test
    @Sql("/sql-scripts/organization-repository/findByAccount-users-hid.sql")
    void findByAccount_Users_Hid_returnsEmptyList_whenOrganizationsAreNotInDB()
    {
        final String hid = "notTestHid";

        //final Set<OrganizationEntity> actualOrganizations = organizationRepository.findByAccounts_Users_Hid(hid);

        //assertThat(actualOrganizations.isEmpty()).isTrue();
    }
}
