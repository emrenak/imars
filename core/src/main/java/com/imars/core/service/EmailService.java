package com.imars.core.service;

public interface EmailService {

	public void sendEmail(String userTo, String subject, String text);
}
