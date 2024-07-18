package com.giansiccardi.notification.service;

import com.giansiccardi.notification.enums.EmailTemplates;
import com.giansiccardi.notification.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.giansiccardi.notification.enums.EmailTemplates.ORDER_CONFIRMATION;
import static com.giansiccardi.notification.enums.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

private final JavaMailSender mailSender;
private final SpringTemplateEngine templateEngine;
//acordate de colocar la anotacion @EnableAsync en el main
@Async
    public void sentPaymentSuccesEmail(
            String destinationEmail,
            String clientName,
            BigDecimal amount,
            String orderReference
) throws MessagingException {
    MimeMessage mimeMessage=mailSender.createMimeMessage();
    MimeMessageHelper messageHelper=
            new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
   messageHelper.setFrom("giansicca70@gmail.com");
   final String templateName= PAYMENT_CONFIRMATION.getTemplate();


   Map<String,Object> variables =new HashMap<>();
   variables.put("clientname",clientName);
   variables.put("amount",amount);
   variables.put("orderReference",orderReference);

   // de .thymeleaf
    Context context = new Context();
    context.setVariables(variables);
    messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

    try{
        String htmlTemplate=templateEngine.process(templateName,context);
        messageHelper.setText(htmlTemplate,true);
        messageHelper.setTo(destinationEmail);
        mailSender.send(mimeMessage);
        log.info(String.format("EMAIL ENVIADO",destinationEmail,templateName));
    }catch (MessagingException e ){
        log.warn("NO SE PUDO ENVIAR EL EMAIL",destinationEmail);
    }
}
    @Async
    public void sentOrderSuccesEmail(
            String destinationEmail,
            String clientName,
            BigDecimal amount,
            String orderReference,
            List<Product> productList
    ) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper messageHelper=
                new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("giansicca70@gmail.com");
        final String templateName= ORDER_CONFIRMATION.getTemplate();


        Map<String,Object> variables =new HashMap<>();
        variables.put("clientname",clientName);
        variables.put("totalAmount",amount);
        variables.put("orderReference",orderReference);
        variables.put("products",productList);

        // de .thymeleaf
        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try{
            String htmlTemplate=templateEngine.process(templateName,context);
            messageHelper.setText(htmlTemplate,true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("EMAIL ENVIADO",destinationEmail,templateName));
        }catch (MessagingException e ){
            log.warn("NO SE PUDO ENVIAR EL EMAIL",destinationEmail);
        }
    }



}
