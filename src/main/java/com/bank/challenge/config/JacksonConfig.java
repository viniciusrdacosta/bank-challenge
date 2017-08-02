package com.bank.challenge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static java.time.ZoneOffset.UTC;
import static java.util.TimeZone.getTimeZone;

@Configuration
public class JacksonConfig {

  private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssz";

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
      .setTimeZone(getTimeZone(UTC))
      .setDateFormat(new SimpleDateFormat(DATE_FORMAT_PATTERN))
      .setSerializationInclusion(NON_EMPTY)
      .registerModule(new JavaTimeModule());
  }

}
