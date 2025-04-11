package com.WebGenerator.App.infrastructure.repository;

import com.WebGenerator.App.domain.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<Img, Long> {
}
