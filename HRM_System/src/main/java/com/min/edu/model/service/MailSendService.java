package com.min.edu.model.service;


import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.min.edu.model.mapper.IEmployeeDao;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;




@Service
public class MailSendService {
	

	
    @Autowired   //Spring 에서 제공하는 MailSender. 
    private JavaMailSenderImpl mailSender;
    
    private String getKey(int size) {
        StringBuilder authKey = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < size; i++) {
            authKey.append(random.nextInt(10));  // 0-9까지의 랜덤 숫자 생성
        }
        return authKey.toString();
    }

    public String sendAuthMail(String mail)  throws MessagingException{
    	
        String authKey = getKey(6);
        MimeMessage mailMessage = mailSender.createMimeMessage();
        String mailContent = "인증번호:   "+authKey ;     //보낼 메시지 
            mailMessage.setSubject("비밀번호재설정", "utf-8"); 
            mailMessage.setText(mailContent, "utf-8");  
            mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            mailSender.send(mailMessage);
        
          return authKey;
    }
    

    
 
    
   
    
}








