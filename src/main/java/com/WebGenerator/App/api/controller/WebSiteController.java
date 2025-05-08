package com.WebGenerator.App.api.controller;


import com.WebGenerator.App.api.dto.UserDto;
import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.api.mapper.UserMapper;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.domain.model.Img;
import com.WebGenerator.App.domain.model.QRCodeModel;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.domain.service.IWebSiteService;
import com.WebGenerator.App.infrastructure.service.MailService;
import com.WebGenerator.App.infrastructure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/websites")
public class WebSiteController {

    private Map<EmailTextProvider.Language, String> assunto;

    public WebSiteController(){
        this.assunto = new HashMap<>();
        assunto.put(EmailTextProvider.Language.EN, "Your website is ready");
        assunto.put(EmailTextProvider.Language.ES, "Tu sitio está listo");
        assunto.put(EmailTextProvider.Language.PT, "Seu site está pronto");
    }

    @Autowired
    private IWebSiteService webSiteService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public List<WebSiteDto> All(){
        return webSiteService.allWebSites();
    }

    @GetMapping("/sorted-by")
    public List<WebSiteDto> All(@RequestParam String param){
        Sort sort = Sort.by(Sort.Direction.ASC, param);
        return webSiteService.allWebSitesSortedBy(sort);
    }

    // passa a ser uma rota para fins de testes de envio de emails e criação de site sem esta atrelado a um usuario
    @PostMapping("/")
    public WebSiteDto create(
            @RequestBody WebSiteDto webSite,
            @RequestParam EmailTextProvider.Language language,
            @RequestParam QRCodeModel qrModel)
    {
        webSite.setIdiom(language.name());

        WebSiteDto webSiteDto = webSiteService.create(webSite);

        // recuperar usuario
        Long idUser = webSiteDto.getUser().getId();
        UserDto userRecover = userService.getFirstUserById(idUser);
        // setar no dto de retorno do website
        webSiteDto.setUser(userMapper.userDtoToUserModel(userRecover));

        String email = webSiteDto.getUser().getEmail();

        if(webSiteDto != null){
            mailService.sendEmail(
                    email,
                    assunto.get(language),
                    mailService.renderHtmlFromTemplate(webSiteDto.getUrlWebSite(), language, qrModel)
            );
        }
        return webSiteDto;
    }

    @PostMapping("/add-img")
    public Img addImg(@RequestParam Long websiteId, @RequestParam("file") MultipartFile file){
        WebSite webSite = webSiteService.getWebSiteById(websiteId);
        return  webSiteService.addImg(webSite, file);
    }

    @GetMapping("/get-img")
    public List<Img> getImg(@RequestParam Long websiteId){
        WebSite webSite = webSiteService.getWebSiteById(websiteId);
        return webSite.getImgs();
    }

    @GetMapping("/search-music")
    public Track[] searchMusic(@RequestParam String search, @RequestParam int limit){
        return webSiteService.listMusic(search, limit);
    }

    @GetMapping("/links-user/{idUser}")
    public List<String> userListLinks(@PathVariable Long idUser){
        return webSiteService.getLinksUser(idUser);
    }
}
