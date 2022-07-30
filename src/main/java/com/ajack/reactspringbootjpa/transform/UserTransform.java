package com.ajack.reactspringbootjpa.transform;

import com.ajack.reactspringbootjpa.model.api.UserApi;
import com.ajack.reactspringbootjpa.model.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserTransform
{
    public UserTransform()
    {}

    public UserApi transformEntityToApi(
        final UserEntity userEntity)
    {
        log.debug("Enter UserTransform.transformEntityToApi; {}", userEntity);

        return UserApi.builder()
            .firstName(userEntity.getFirstName())
            .id(userEntity.getId())
            .lastName(userEntity.getLastName())
            .build();
    }
}
