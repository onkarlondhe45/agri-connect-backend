package com.agriconnect.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	@Autowired
	JavaMailSender mailSender;

	public void sendMail(String subject, String body, String to, boolean isHtml) {
	    MimeMessage message = mailSender.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(body, isHtml); // true to send as HTML

	        mailSender.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        // Handle exception
	    }
	}
}
