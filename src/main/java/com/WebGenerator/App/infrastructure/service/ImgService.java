package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.domain.model.Img;
import com.WebGenerator.App.infrastructure.repository.ImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgService {
    @Autowired
    private ImgRepository imgRepository;

    public Img create(Img img){
        return imgRepository.save(img);
    }
}
