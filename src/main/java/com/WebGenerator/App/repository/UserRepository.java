package com.WebGenerator.App.repository;

import com.WebGenerator.App.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);

    @Query(value = "From User")
    List<User> all(Sort sort);
}


