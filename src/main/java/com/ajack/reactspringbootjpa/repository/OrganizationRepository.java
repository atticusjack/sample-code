package com.ajack.reactspringbootjpa.repository;

import com.ajack.reactspringbootjpa.model.entity.OrganizationEntity;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, UUID>
{
    Set<OrganizationEntity> findByAccounts_Users_Hid(String hid);
}
