package com.min.edu.controller;

import com.min.edu.model.mapper.IEmployeeDao;
import com.min.edu.model.service.MailSendService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class MailController {
	
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private IEmployeeDao dao;
	
	
	
	
	
	@PostMapping(value = "/sendEmail.do")
	public String sendEmail(@RequestParam("email") String email,
			                @RequestParam("emp_id") String emp_id, 
			                Model model) {
		log.info("{}",emp_id);
		log.info("{}",email);
		
		//이메일이 db에 이메일과 일치할 경우에만 전송
		  if (email.equals(dao.checkEmail(emp_id))){
			try {
		    	
		    	
		        // 인증 메일 전송
		        String authKey = mailSendService.sendAuthMail(email);
		        
		        model.addAttribute("emailExists", true); 
		        model.addAttribute("authKey",authKey);
		        model.addAttribute("alertMessage", "메일을 확인해 주세요");
		        model.addAttribute("alertType", "success"); 
		    } catch (MessagingException e) {
		        
		    	 model.addAttribute("alertMessage", "오류");
			     model.addAttribute("alertType", "error"); 
		    }
		}else {
			 model.addAttribute("alertMessage", "등록된 이메일을 입력해주세요");
		     model.addAttribute("alertType", "error"); 
		}
		
	    return "forgot";
	}
	
 
	
	
	
	
	
	
	

	}

    




