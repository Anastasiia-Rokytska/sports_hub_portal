package com.company.sportHubPortal.Models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;


@Entity
//@Bean
public class EmailMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;
  String email;
  String subject;
  String text;

  @Enumerated(EnumType.STRING)
  MessageType type;

  public EmailMessage() {
  }

  @Autowired
  public EmailMessage(String email, String subject, String text, MessageType type) {
    this.email = email;
    this.subject = subject;
    this.text = text;
    this.type = type;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }
}
