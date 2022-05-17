package com.company.sportHubPortal.Services;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

  private final JavaMailSenderImpl javaMailSender;

  @Autowired
  public EmailSenderService(JavaMailSenderImpl javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Async
  public void sendTextMessage(String email, String subject, String text) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
    simpleMailMessage.setTo(email);
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText(text);
    javaMailSender.send(simpleMailMessage);
  }
}
