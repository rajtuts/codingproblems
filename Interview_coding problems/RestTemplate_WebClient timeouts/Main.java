package com.example.clientconfig;

import com.example.clientconfig.service.DownstreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
class Main {
  static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}

@Configuration
class DemoRunnerConfig {
  private static final Logger log = LoggerFactory.getLogger(DemoRunnerConfig.class);

  @Bean
  CommandLineRunner demo(DownstreamService service) {
    return args -> {
      try {
        String r1 = service.callWithRestTemplate();
        log.info("RestTemplate result: {}", r1);
      } catch (Exception e) {
        log.warn("RestTemplate call failed: {}", e.toString());
      }

      try {
        String r2 = service.callWithWebClientBlockingForDemo();
        log.info("WebClient result: {}", r2);
      } catch (Exception e) {
        log.warn("WebClient call failed: {}", e.toString());
      }
    };
  }
}
