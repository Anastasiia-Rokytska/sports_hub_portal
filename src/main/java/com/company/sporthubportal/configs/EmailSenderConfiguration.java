package com.company.sporthubportal.configs;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailSenderConfiguration {
  @Bean
  public JavaMailSenderImpl configureJavaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setUsername("sports.hub.portal.0@gmail.com");
    javaMailSender.setPassword("tsaeptlbupenfiah");
    javaMailSender.setHost("smtp.gmail.com");
    javaMailSender.setPort(587);
    Properties props = javaMailSender.getJavaMailProperties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    return javaMailSender;
  }
}
