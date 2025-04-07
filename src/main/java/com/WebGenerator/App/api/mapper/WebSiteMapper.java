package com.WebGenerator.App.api.mapper;

import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.model.WebSite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebSiteMapper {
    WebSiteDto webSiteModelToWebSiteDto(WebSite webSite);
    WebSite webSiteDtoToWebSiteModel(WebSiteDto webSiteDto);
}
