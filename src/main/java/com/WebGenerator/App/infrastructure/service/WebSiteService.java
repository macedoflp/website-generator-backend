package com.WebGenerator.App.infrastructure.service;

import java.util.List;
import java.util.stream.Collectors;

import com.WebGenerator.App.api.controller.util.exception.WebSiteNotFoundException;
import com.WebGenerator.App.domain.model.Img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.api.mapper.WebSiteMapper;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.domain.service.IWebSiteService;
import com.WebGenerator.App.infrastructure.repository.WebSiteRespository;
import org.springframework.web.multipart.MultipartFile;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
public class WebSiteService implements IWebSiteService {

    @Autowired
    private WebSiteRespository webSiteRespository;

    @Autowired
    private ImgService imgService;

    @Autowired
    private SpotifyApi spotifyApi;

    @Autowired
    private WebSiteMapper webSiteMapper;

    @Override
    public WebSiteDto create(WebSiteDto webSiteDto){
        WebSite webSiteSave = webSiteMapper.webSiteDtoToWebSiteModel(webSiteDto);
        webSiteRespository.save(webSiteSave);
        return webSiteMapper.webSiteModelToWebSiteDto(webSiteSave);
    }

    public Img addImg(WebSite webSite, MultipartFile file){
        String urlImg = imgService.uplodImg(file);
        Img imgNew = new Img();
        imgNew.setImgUrl(urlImg);
        webSite.addImg(imgNew);
        imgNew.setWebsite(webSite);
        return imgService.create(imgNew);
    }

    @Override
    public List<WebSiteDto> allWebSites(){
        return webSiteRespository.findAll()
                .stream()
                .map(webSiteMapper::webSiteModelToWebSiteDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WebSiteDto> allWebSitesSortedBy(Sort sort){
        return webSiteRespository.findAll(sort)
                .stream()
                .map(webSiteMapper::webSiteModelToWebSiteDto)
                .collect(Collectors.toList());
    }

    public WebSite getWebSiteById(Long id) {
        return webSiteRespository.findById(id)
                .orElseThrow(WebSiteNotFoundException::new);
    }

    public Track[] listMusic(String s, int limit){

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

//            System.err.println("Expires in: " + clientCredentials.getExpiresIn());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(s)
                .limit(limit)
                .build();
        try {
            Paging<Track> trackPaging = searchTracksRequest.execute();
            Track[] tracks = trackPaging.getItems();
            return tracks;

        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
