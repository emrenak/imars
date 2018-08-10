package com.imars.core.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import com.imars.core.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender  = new JavaMailSenderImpl();
    
	private static final String userFrom="imars@imars.com";
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    
	public void sendEmail(String userTo, String subject, String text) {
		SimpleMailMessage message = composeEmailMessage(userTo,subject,text);
        logger.info("Sending mail");
        try {
        	javaMailSender.send(message);	
		} catch (Exception e) {
			logger.error("Error occured during sending mail. ex: " + e.getMessage());
		}
		
	}
	
    private SimpleMailMessage composeEmailMessage(String userTo, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userTo);
        mailMessage.setReplyTo(userFrom);
        mailMessage.setFrom(userFrom);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        return mailMessage;
    }
	

}
