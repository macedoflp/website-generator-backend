package com.WebGenerator.App.infrastructure.repository;

import com.WebGenerator.App.domain.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByName(String name);

    @Query(value = "SELECT u FROM User u WHERE u.email = ?1")
    User findFirstByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(value = "From User")
    List<User> all(Sort sort);

    @Query(value = "From User")
    List<User> allUsers();
}


