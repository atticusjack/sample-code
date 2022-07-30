package com.ajack.reactspringbootjpa.repository;

import com.ajack.reactspringbootjpa.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID>
{
    Optional<AccountEntity> findById(UUID id);

    List<AccountEntity> findByName(String name);

    List<AccountEntity> findDistinctByOrganizations_EinIsIn(List<String> eins);
}
