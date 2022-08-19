package com.ajack.reactspringbootjpa.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "accounts")
    private Set<OrganizationEntity> organizations = new LinkedHashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "account")
    private Set<UserEntity> users;
}
