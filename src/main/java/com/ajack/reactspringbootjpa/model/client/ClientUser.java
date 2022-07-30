package com.ajack.reactspringbootjpa.model.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ClientUser
{
    private String hid;
    private List<ClientPolicy> policies;
}
