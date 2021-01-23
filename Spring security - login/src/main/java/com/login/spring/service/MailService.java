package com.login.spring.service;

import com.login.spring.model.RegistrationMail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class MailService {

    private final static int HOURS = 1;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void sendMail(RegistrationMail registrationMail){
        MimeMessagePreparator message = mimeMessage ->{
            Context context = new Context();
            context.setVariable("user", registrationMail.getName());
            context.setVariable("hours", HOURS);
            context.setVariable("tokenUrl", registrationMail.getTokenUrl());
            String process = templateEngine.process("mailTemplate", context);


            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("test@test.com");
            messageHelper.setTo(registrationMail.getRecipient());
            messageHelper.setSubject(registrationMail.getSubject());
            //messageHelper.setText(registrationMail.getBody());
            messageHelper.setText(process,true);
        };
        //try{
        mailSender.send(message);
        //}
//        catch (MailException exception){
//
//        }
    }


}
