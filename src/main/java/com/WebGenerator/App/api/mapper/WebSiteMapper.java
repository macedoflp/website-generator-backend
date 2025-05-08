package com.WebGenerator.App.api.mapper;

import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.model.WebSite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WebSiteMapper {
    @Mapping(source = "urlWebSite", target = "urlWebSite")
    @Mapping(source = "idiom", target = "idiom")
    WebSiteDto webSiteModelToWebSiteDto(WebSite webSite);
    WebSite webSiteDtoToWebSiteModel(WebSiteDto webSiteDto);
}
