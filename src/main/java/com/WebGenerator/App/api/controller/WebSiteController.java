package com.WebGenerator.App.api.controller;


import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.domain.model.Img;
import com.WebGenerator.App.domain.model.QRCodeModel;
import com.WebGenerator.App.domain.model.WebSite;
import com.WebGenerator.App.domain.service.IWebSiteService;
import com.WebGenerator.App.infrastructure.service.MailService;
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
    @PostMapping
    public WebSiteDto create(
            @RequestBody WebSiteDto webSite,
            @RequestParam EmailTextProvider.Language language,
            @RequestParam QRCodeModel qrModel)
    {
        WebSiteDto webSiteDto = webSiteService.create(webSite);

        if(webSiteDto != null){
            mailService.sendEmail(
                    webSiteDto.getUser().getEmail(),
                    assunto.get(language),
                    mailService.renderHtmlFromTemplate(webSiteDto.getUrlWebSite(), language, qrModel)
            );
        }
        return webSiteDto;
    }

    @PostMapping(path = "/add-img/{idWebSite}")
    public Img addImg(@PathVariable Long idWebSite, @RequestParam("file") MultipartFile file){
        WebSite webSite = webSiteService.getWebSiteById(idWebSite);
        return  webSiteService.addImg(webSite, file);
    }

    @GetMapping("/get-img/{idWebSite}")
    public List<Img> getImg(@PathVariable Long idWebSite){
        WebSite webSite = webSiteService.getWebSiteById(idWebSite);
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
