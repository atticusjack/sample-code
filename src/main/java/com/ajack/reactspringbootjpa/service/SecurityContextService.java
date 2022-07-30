package com.ajack.reactspringbootjpa.service;

import com.ajack.reactspringbootjpa.model.security.UserSecurity;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService
{
    public SecurityContextService()
    {
    }

    public UserSecurity getCurrentlyLoggedInUser()
    {
        return UserSecurity.builder()
            .firstName("Jack")
            .hid("000000")
            .lastName("Foo")
            .build();
    }

    public String getCurrentlyLoggedInUserHid()
    {
        return this.getCurrentlyLoggedInUser().getHid();
    }
}
