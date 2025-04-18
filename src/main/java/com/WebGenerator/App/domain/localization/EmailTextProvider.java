package com.WebGenerator.App.domain.localization;

import java.util.HashMap;
import java.util.Map;

public class EmailTextProvider {

    public enum Language {
        PT, EN, ES
    }

    private static final Map<Language, Map<String, String>> translations = new HashMap<>();

    static {
        Map<String, String> pt = new HashMap<>();
        pt.put("nameAppEmail", "Love Timeline.");
        pt.put("titleEmail", "Olá ");
        pt.put("subTitleEmail", "Obrigado por usar o ");
        pt.put("labelInfoOfDetails", "Aqui estão os detalhes do seu site:");
        pt.put("labelLinkForYourWebSite", "Link do seu site:");
        pt.put("qrCodeInfoEmail", "Clique na imagem abaixo para baixar seu QR Code");
        pt.put("actionEmail", "Em caso de dúvidas ou se precisar de ajuda, é só responder este email!");
        pt.put("footerP1Email", "Atenciosamente");
        pt.put("footerP2Email", "Equipe MDK Studio");
        pt.put("footerP3Email", "Transformando ideias em soluções digitais");

        Map<String, String> en = new HashMap<>();
        en.put("nameAppEmail", "Love Timeline.");
        en.put("titleEmail", "Hello ");
        en.put("subTitleEmail", "Thank you for using ");
        en.put("labelInfoOfDetails", "Here are the details of your site:");
        en.put("labelLinkForYourWebSite", "Link to your website:");
        en.put("qrCodeInfoEmail", "Click the image below to download your QR Code");
        en.put("actionEmail", "If you have any questions or need help, just reply to this email!");
        en.put("footerP1Email", "Sincerely");
        en.put("footerP2Email", "MDK Studio Team");
        en.put("footerP3Email", "Turning ideas into digital solutions");

        Map<String, String> es = new HashMap<>();
        es.put("nameAppEmail", "Love Timeline.");
        es.put("titleEmail", "Hola ");
        es.put("subTitleEmail", "Gracias por usar ");
        es.put("labelInfoOfDetails", "Aquí están los detalles de tu sitio:");
        es.put("labelLinkForYourWebSite", "Enlace de tu sitio web:");
        es.put("qrCodeInfoEmail", "Haz clic en la imagen de abajo para descargar tu código QR");
        es.put("actionEmail", "¡Si tienes dudas o necesitas ayuda, solo responde este correo!");
        es.put("footerP1Email", "Atentamente");
        es.put("footerP2Email", "Equipo de MDK Studio");
        es.put("footerP3Email", "Transformando ideas en soluciones digitales");

        translations.put(Language.PT, pt);
        translations.put(Language.EN, en);
        translations.put(Language.ES, es);
    }

    public static Map<String, String> getText(Language lang) {
        return translations.getOrDefault(lang, translations.get(Language.PT));
    }
}
