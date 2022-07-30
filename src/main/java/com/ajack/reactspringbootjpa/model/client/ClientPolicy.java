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
public class ClientPolicy
{
    private String customerNumber;
    private String policyNumber;
    private List<String> ein;
}
