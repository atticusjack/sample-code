package com.ajack.reactspringbootjpa.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "organizations")
public class OrganizationEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String customerNumber;
    private String ein;
    private String policyNumber;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "organization")
    private Set<ReportEntity> reports;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "organization")
    Set<AccountOrganizationEntity> accounts;
}
