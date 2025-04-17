package com.WebGenerator.App.domain.service;

import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.model.Img;
import com.WebGenerator.App.domain.model.WebSite;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.util.List;

public interface IWebSiteService {
    public WebSiteDto create(WebSiteDto webSiteDto);
    public List<WebSiteDto> allWebSites();
    public List<WebSiteDto> allWebSitesSortedBy(Sort sort);
    public Img addImg(WebSite webSite, MultipartFile file);
    public WebSite getWebSiteById(Long id);
    public Track[] listarMusicas(String s);
}
