package com.ajack.reactspringbootjpa.repository;

import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, UUID>
{
}