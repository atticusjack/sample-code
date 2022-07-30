package com.ajack.reactspringbootjpa.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserApi
{
    private UUID id;
    private String firstName;
    private String lastName;
}
