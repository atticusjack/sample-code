package com.ajack.reactspringbootjpa.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "accounts_organizations")
public class AccountOrganizationEntity
{
    @Id
    UUID id;
    LocalDateTime creteTimestamp;
    LocalDateTime deleteTimestamp;

    @ManyToOne
    @JoinColumn(name = "account_id")
    AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    OrganizationEntity organization;
}
