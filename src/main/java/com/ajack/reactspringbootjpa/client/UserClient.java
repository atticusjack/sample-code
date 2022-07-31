package com.ajack.reactspringbootjpa.client;

import com.ajack.reactspringbootjpa.model.client.ClientPolicy;
import com.ajack.reactspringbootjpa.model.client.ClientUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserClient
{
    public UserClient()
    {}

    public Optional<ClientUser> getPolicyInformationForUser(
        final String hid)
    {
        if (hid.equals("000000"))
        {
            return Optional.of(
                ClientUser.builder()
                    .firstName("test")
                    .hid("000000")
                    .lastName("sonOfTest")
                    .policies(List.of(
                        ClientPolicy.builder()
                            .customerNumber("customer 1")
                            .ein(List.of(
                                "01-234567",
                                "90-876543"))
                            .policyNumber("policy 1")
                            .build(),
                        ClientPolicy.builder()
                            .customerNumber("customer 1")
                            .ein(List.of(
                                "01-123456"))
                            .policyNumber("policy 2")
                            .build(),
                        ClientPolicy.builder()
                            .customerNumber("customer 2")
                            .ein(List.of(
                                "90-123456"))
                            .policyNumber("policy 3")
                            .build()))
                    .build());
        }
        else
        {
            return Optional.empty();
        }
    }
}
