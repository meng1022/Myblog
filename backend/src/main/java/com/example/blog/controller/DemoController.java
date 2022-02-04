package com.example.blog.controller;

import com.example.blog.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Value("${spring.mail.username}")
    private String fromemail;
    @Autowired
    JavaMailSenderImpl mailSender;

    private final String[] codes = new String[]{"jiowkgsmcues","ficisocowjc","phiuowmxmq","eydui3829cw"};

    @GetMapping("/api/sendemail")
    public Result sendEmail(@RequestParam("email")String email){
        try{
            String code = codes[Math.abs((int)System.currentTimeMillis()%4)];
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromemail);
            message.setTo(email);
            message.setSubject("[JavaMail Demo Test]-Verification Code");
            message.setText(code);
            mailSender.send(message);
            return Result.SetOk(code);
        }catch (Exception e){
            return Result.SetError(e.getMessage());
        }
    }
}
