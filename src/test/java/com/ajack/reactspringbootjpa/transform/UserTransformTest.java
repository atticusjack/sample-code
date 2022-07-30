package com.ajack.reactspringbootjpa.transform;

import com.ajack.reactspringbootjpa.model.api.UserApi;
import com.ajack.reactspringbootjpa.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserTransformTest
{
    private UserTransform userTransform;

    @BeforeEach
    void beforeEach()
    {
        this.userTransform = new UserTransform();
    }

    @Test
    void transformEntityToApi_returnsUserApi_whenUserEntityIsValid()
    {
        final UUID userId = UUID.randomUUID();
        final UserEntity userEntity = UserEntity.builder()
            .firstName("foo")
            .hid("baz")
            .id(userId)
            .lastName("bar")
            .build();

        final UserApi actualUser = userTransform.transformEntityToApi(userEntity);

        assertThat(actualUser).isEqualTo(UserApi.builder()
            .firstName("foo")
            .id(userId)
            .lastName("bar")
            .build());
    }
}
