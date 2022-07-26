package com.company.sportHubPortal.Services;

import com.company.sportHubPortal.Controllers.UserController;
import com.company.sportHubPortal.Models.EmailSender;
import com.company.sportHubPortal.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class EmailSenderService  implements DisposableBean{

  private EmailSender emailSender;
  private final UserRepository userRepository;
  Logger logger = LoggerFactory.getLogger(UserController.class);
  private final ScheduledExecutorService executor;

  EmailSenderService(ScheduledExecutorService executor, UserRepository userRepository){
    this.userRepository = userRepository;
    this.executor = executor;
  }

  public EmailSender getEmailSender() {
    return emailSender;
  }

  public void setEmailSender(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  public boolean sendEmailInSeparateThread() {
    if (emailSender.addToMessageQueue(userRepository)) {
      executor.schedule(emailSender, (EmailSender.getMessageQueue().size()-1)*30, TimeUnit.SECONDS);
      EmailSender.getMessageQueue().remove(emailSender.getEmailMessage());
      logger.info("Message is scheduled");
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public void destroy(){
    executor.shutdown();
  }

}
