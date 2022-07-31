package com.ajack.reactspringbootjpa.controller;

import com.ajack.reactspringbootjpa.model.api.OrganizationApi;
import com.ajack.reactspringbootjpa.service.OrganizationService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(OrganizationController.class)
public class OrganizationControllerContractTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationService organizationService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach()
    {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void getOrganizations_callsOrganizationService_andReturnsListOfOrganizationApi()
        throws Exception
    {
        final OrganizationApi expectedOrganization = OrganizationApi.builder()
            .customerNumber("customerNumber")
            .ein("ein")
            .policyNumber("policyNumber")
            .build();

        when(organizationService.getOrganizations()).thenReturn(List.of(expectedOrganization));

        final MockHttpServletRequestBuilder request = get("/api/v1/organizations")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        final MockHttpServletResponse response = mockMvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().getResponse();

        assertThat(response).isNotNull();

        final List<OrganizationApi> actualOrganization = objectMapper.readValue(
            response.getContentAsString(),
            objectMapper.getTypeFactory().constructCollectionType(List.class, OrganizationApi.class));

        assertThat(actualOrganization).isEqualTo(List.of(expectedOrganization));
    }
}
