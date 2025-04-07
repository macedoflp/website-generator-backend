package com.WebGenerator.App.infrastructure.repository;

import com.WebGenerator.App.domain.model.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebSiteRespository extends JpaRepository<WebSite, Long> {
}
