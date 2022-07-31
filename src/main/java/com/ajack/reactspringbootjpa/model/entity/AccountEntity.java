package com.ajack.reactspringbootjpa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "accounts")
public class AccountEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id = UUID.randomUUID();
    private String name;

    @OneToMany(mappedBy = "account")
    private List<OrganizationEntity> organizations;

    @OneToMany
    private List<UserEntity> users;
}
