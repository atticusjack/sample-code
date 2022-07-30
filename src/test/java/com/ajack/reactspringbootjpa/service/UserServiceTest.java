package com.ajack.reactspringbootjpa.service;

import com.ajack.reactspringbootjpa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
    @Mock
    private SecurityContextService securityContextService;

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void beforeEach()
    {
        userService = new UserService();
    }

    @Test
    void getUser_callsSecurityContextServiceForHid_thenUsesHidToLookForUserInDB()
    {

    }
}
