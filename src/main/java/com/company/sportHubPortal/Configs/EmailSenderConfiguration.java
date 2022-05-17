package com.company.sportHubPortal.Configs;

import java.util.Properties;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class EmailSenderConfiguration {
    private final String EMAIL_CONFIG_PATH = "src/main/resources/configs/emailConfigs.json";
    private final Logger logger = LoggerFactory.getLogger(EmailSenderConfiguration.class);
    @Bean
    public JavaMailSenderImpl configureJavaMailSender(){

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(EMAIL_CONFIG_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            String username = (String) jsonObject.get("username");
            String password = (String) jsonObject.get("password");
            String host = (String) jsonObject.get("host");
            int port = ((Long)jsonObject.get("port")).intValue();

            javaMailSender.setUsername(username);
            javaMailSender.setPassword(password);
            javaMailSender.setHost(host);
            javaMailSender.setPort(port);

            Properties props = javaMailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            logger.info("JavaMailSender is successfully configured");
        } catch (IOException | ParseException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }


        return javaMailSender;
    }
}
