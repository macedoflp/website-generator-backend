package com.WebGenerator.App.domain.repository;

import com.WebGenerator.App.domain.models.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebSiteRespository extends JpaRepository<WebSite, Long> {
}
