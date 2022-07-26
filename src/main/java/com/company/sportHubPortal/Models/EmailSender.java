package com.company.sportHubPortal.Models;


import com.company.sportHubPortal.Repositories.UserRepository;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;


public class EmailSender implements Runnable {

  private final JavaMailSenderImpl javaMailSender;
  private EmailMessage emailMessage;
  Logger logger = LoggerFactory.getLogger(EmailSender.class);
  private final String MESSAGE_QUEUE_CONFIG_PATH =
      "src/main/resources/configs/messageQueueConfigs.json";
  int capacity;
  private static Queue<EmailMessage> messageQueue;

  public EmailSender(JavaMailSenderImpl javaMailSender, EmailMessage emailMessage) {
    this.javaMailSender = javaMailSender;
    int queueCapacity = 100;
    try {
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(new FileReader(MESSAGE_QUEUE_CONFIG_PATH));
      JSONObject jsonObject = (JSONObject) obj;
      queueCapacity = ((Long) jsonObject.get("capacity")).intValue();

    } catch (IOException | ParseException e) {
      logger.info(e.getMessage());
    }
    this.capacity = queueCapacity;
    EmailSender.messageQueue = new LinkedBlockingQueue(this.capacity);
    this.emailMessage = emailMessage;
  }


  public static Queue<EmailMessage> getMessageQueue() {
    return messageQueue;
  }

  public EmailMessage getEmailMessage() {
    return emailMessage;
  }


  public boolean addToMessageQueue(UserRepository userRepository) {
    if (userRepository.getUserByEmail(this.emailMessage.getEmail()) != null
        && !isInQueue(this.emailMessage)) {
      EmailSender.messageQueue.add(this.emailMessage);
      logger.info("Email message is successfully added to Message Queue");
      return true;
    } else {
      logger.info("Email message is not added to Message Queue");
      return false;
    }
  }

  public boolean isInQueue(EmailMessage emailMessage) {
    for (EmailMessage message : messageQueue) {
      if (emailMessage.getEmail().equals(message.getEmail())
          && emailMessage.getType() == message.getType()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void run() {
    sendTextMessage();
  }

  @Async
  public void sendTextMessage() {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
    simpleMailMessage.setTo(emailMessage.getEmail());
    simpleMailMessage.setSubject(emailMessage.getSubject());
    simpleMailMessage.setText(emailMessage.getText());
    javaMailSender.send(simpleMailMessage);
    logger.info("message is sent");
  }


}
