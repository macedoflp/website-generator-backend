package com.WebGenerator.App.infrastructure.service;

import com.WebGenerator.App.api.dto.WebSiteDto;
import com.WebGenerator.App.domain.localization.EmailTextProvider;
import com.WebGenerator.App.domain.model.QRCodeModel;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String remetente;
    @Autowired
    private TemplateEngine templateEngine;

    public String sendEmail(String des, String assunto, String html){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(des);
            helper.setSubject(assunto);
            helper.setText(html, true);
            javaMailSender.send(message);
            return "email enviado!";
        }catch (Exception e){
            System.err.println("Erro: " + e.getMessage());
            return "Erro ao enviar email " + e.getMessage();
        }
    }

    public String renderHtmlFromTemplate(WebSiteDto webSiteDto, EmailTextProvider.Language language, QRCodeModel modelChosen) {
        Context context = new Context();

//        context.setVariable("title", contentEmail.getTitleEmail() + "daniel99korban@gmail.com".split("@")[0]);
        context.setVariable("siteUrl", "https://love-timeline-five.vercel.app/" + webSiteDto.getId() + "/" + webSiteDto.getTitle().replace(" ", "%20"));
        context.setVariable("qrCodeUrl", "https://api.qrserver.com/v1/create-qr-code/?size=170x170&data=https://love-timeline-five.vercel.app/" + webSiteDto.getId() + "/" + webSiteDto.getTitle().replace(" ", "%20"));


        Map<String, String> texts = EmailTextProvider.getText(language);
        texts.forEach(context::setVariable);

        // Fragmento do QRCode
        context.setVariable("qrModel", modelChosen.getTemplateName());

        return templateEngine.process("lovetimeline-email", context);
    }

}
