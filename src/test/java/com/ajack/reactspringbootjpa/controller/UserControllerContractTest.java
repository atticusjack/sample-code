package com.ajack.reactspringbootjpa.controller;

import com.ajack.reactspringbootjpa.model.api.UserApi;
import com.ajack.reactspringbootjpa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerContractTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach()
    {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void getUser_callsUserService_andReturnsUser()
        throws Exception
    {
        final UserApi expectedUser = UserApi.builder()
            .id(UUID.randomUUID())
            .firstName("Joe")
            .lastName("Schmoe")
            .build();

        when(userService.getUser()).thenReturn(expectedUser);

        final MockHttpServletRequestBuilder request = get("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        final MockHttpServletResponse response = mockMvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().getResponse();

        assertThat(response).isNotNull();

        final UserApi actualUser = objectMapper.readValue(
            response.getContentAsString(),
            UserApi.class);

        assertThat(actualUser).isEqualTo(expectedUser);
    }
}
