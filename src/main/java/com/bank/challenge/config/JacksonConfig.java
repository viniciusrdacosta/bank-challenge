package com.bank.challenge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static java.time.ZoneOffset.UTC;
import static java.util.TimeZone.getTimeZone;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
      .setTimeZone(getTimeZone(UTC))
      .setSerializationInclusion(NON_EMPTY)
      .registerModule(new JavaTimeModule());
  }

}
