package com.bank.challenge.controller.model;

import com.bank.challenge.controller.validation.NotBeforeSixtySecondsAgo;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;

@Value
@NotBeforeSixtySecondsAgo
public class TransactionRequest {

  ZonedDateTime requestDate = now(UTC);

  @NotNull(message = "Amount should not be null")
  BigDecimal amount;

  @NotNull(message = "Timestamp should not be null")
  ZonedDateTime timestamp;

}
