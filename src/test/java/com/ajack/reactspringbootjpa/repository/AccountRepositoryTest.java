package com.ajack.reactspringbootjpa.repository;

import com.ajack.reactspringbootjpa.model.entity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
    "spring.test.database.replace=NONE"
})
@Testcontainers
class AccountRepositoryTest
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
    private AccountRepository accountRepository;

    @Test
    @Sql("/sql-scripts/account-repository/create-accounts.sql")
    void findById_returnsAccountEntity_whenAccountIsInDB()
    {
        final AccountEntity expectedAccount = AccountEntity.builder()
            .name("New Account")
            .build();

        final AccountEntity savedAccount = accountRepository.save(expectedAccount);

        final Optional<AccountEntity> actualAccount = accountRepository.findById(savedAccount.getId());

        assertThat(actualAccount.get()).isEqualTo(savedAccount);
    }

    @Test
    @Sql("/sql-scripts/account-repository/create-accounts.sql")
    void findById_returnsEmptyOptional_whenAccountIsNotInDB()
    {
        accountRepository.save(
            AccountEntity.builder()
                .name("New Account")
                .build());

        final Optional<AccountEntity> actualAccount = accountRepository.findById(UUID.randomUUID());

        assertThat(actualAccount.isEmpty()).isTrue();
    }

    @Test
    @Sql("/sql-scripts/account-repository/create-accounts-with-duplicate-names.sql")
    void findByName_returnsAllAccountsWithTheSameName_whenAccountsAreInDB()
    {
        final List<AccountEntity> actualAccounts = accountRepository.findByName("duplicate name");

        assertThat(actualAccounts.size()).isEqualTo(2);
    }

    @Test
    @Sql("/sql-scripts/account-repository/create-accounts-with-duplicate-names.sql")
    void findByName_returnsEmptyList_whenAccountsAreNotInDB()
    {
        final List<AccountEntity> shouldBeEmpty = accountRepository.findByName("not duplicate name");

        assertThat(shouldBeEmpty.isEmpty()).isTrue();
    }

    @Test
    @Sql("/sql-scripts/account-repository/create-accounts-associated-with-organizations.sql")
    void findDistinctByOrganizations_EinIsInn_returnsListOfAccounts_whenAccountsAreInDB()
    {
        final List<String> eins = List.of("ein 1", "ein 2", "ein 3");

        final List<AccountEntity> actualAccounts = accountRepository.findDistinctByOrganizations_DeleteTimestampIsNullAndOrganizations_Organization_EinIn(eins);

        assertThat(actualAccounts.size()).isEqualTo(1);
    }

    @Test
    @Sql("/sql-scripts/account-repository/create-accounts-associated-with-organizations.sql")
    void findDistinctByOrganizations_EinIsInn_returnsEmptyList_whenAccountsAreNotInDB()
    {
        final List<String> eins = List.of("ein 7", "ein 8", "ein 9");

        final List<AccountEntity> actualAccounts = accountRepository.findDistinctByOrganizations_DeleteTimestampIsNullAndOrganizations_Organization_EinIn(eins);

        assertThat(actualAccounts.isEmpty()).isTrue();
    }
}
