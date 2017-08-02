package com.bank.challenge.controller.model;

import com.bank.challenge.controller.validation.NotBeforeSixtySecondsAgo;
import lombok.Value;

import javax.validation.constraints.NotNull;

import static java.time.Instant.now;

@Value
@NotBeforeSixtySecondsAgo
public class TransactionRequest {

  Long requestDateTimestamp = now().toEpochMilli();

  @NotNull(message = "Amount should not be null")
  Double amount;

  @NotNull(message = "Timestamp should not be null.")
  Long timestamp;

}
