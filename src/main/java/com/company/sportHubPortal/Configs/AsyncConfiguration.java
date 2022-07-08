package com.company.sportHubPortal.Configs;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@Configuration
@EnableAsync
public class AsyncConfiguration {

  private final Logger logger = LoggerFactory.getLogger(EmailSenderConfiguration.class);
  private final String ASYNC_CONFIG_PATH = "src/main/resources/configs/asyncConfigs.json";

  @Bean
  public ScheduledExecutorService taskExecutor() {
    logger.debug("Creating Async Task Executor");
    int corePoolSize = 0;
    try {
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(new FileReader(ASYNC_CONFIG_PATH));
      JSONObject jsonObject = (JSONObject) obj;
      corePoolSize = ((Long) jsonObject.get("corePoolSize")).intValue();

    } catch (IOException | ParseException e) {
      logger.info(e.getMessage());
    }
    ScheduledExecutorService executor =  Executors.newScheduledThreadPool(corePoolSize);

    return executor;
  }

}
