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
@Table(name = "organizations")
public class OrganizationEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String customerNumber;
    private String ein;
    private String policyNumber;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @OneToMany
    private List<ReportEntity> reports;
}
