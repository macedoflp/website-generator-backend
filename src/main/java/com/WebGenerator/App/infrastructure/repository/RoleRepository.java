package com.WebGenerator.App.infrastructure.repository;

import com.WebGenerator.App.domain.model.Role;
import com.WebGenerator.App.domain.model.util.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT r FROM Role r WHERE r.name = ?1")
    Optional<Role> findByName(RoleName role);
}
