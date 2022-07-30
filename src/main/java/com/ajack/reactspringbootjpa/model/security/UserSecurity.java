package com.ajack.reactspringbootjpa.model.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserSecurity
{
    private String firstName;
    private String hid;
    private String lastName;
}
