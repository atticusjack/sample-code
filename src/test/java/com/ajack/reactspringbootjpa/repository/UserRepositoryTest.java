package com.ajack.reactspringbootjpa.repository;

import com.ajack.reactspringbootjpa.model.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
    "spring.test.database.replace=NONE"
})
@Testcontainers
class UserRepositoryTest
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
    private UserRepository userRepository;

    @Test
    @Sql("/sql-scripts/user-repository/findUserEntityByHid.sql")
    void findUserEntityByHid_returnsUser_whenUserIsInDB()
    {
        final Optional<UserEntity> actualUser = userRepository.findUserEntityByHid("testHid");

        assertThat(actualUser.isPresent()).isTrue();
        assertThat(actualUser.get().getFirstName()).isEqualTo("first_name");
        assertThat(actualUser.get().getHid()).isEqualTo("testHid");
        assertThat(actualUser.get().getLastName()).isEqualTo("last_name");
        assertThat(actualUser.get().getAccount().getName()).isEqualTo("test user repository account");
    }

    @Test
    @Sql("/sql-scripts/user-repository/findUserEntityByHid.sql")
    void findUserEntityByHid_returnsEmptyOptional_whenUserIsNotInDB()
    {
        final Optional<UserEntity> actualUser = userRepository.findUserEntityByHid("notTestHid");

        assertThat(actualUser.isEmpty()).isTrue();
    }
}
