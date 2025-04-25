package com.WebGenerator.App.infrastructure.repository;

import com.WebGenerator.App.domain.model.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WebSiteRespository extends JpaRepository<WebSite, Long> {
    @Query(value = "SELECT w.urlWebSite FROM WebSite w WHERE w.user_app.Id = ?1")
    List<String> findLinksUser(Long id);
}
