package com.bank.challenge.controller.model;

import com.bank.challenge.controller.validation.NotBeforeSixtySecondsAgo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;


@Value
@NotBeforeSixtySecondsAgo
public class TransactionRequest {

  ZonedDateTime requestDate = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS);

  @NotNull(message = "Amount should not be null")
  BigDecimal amount;

  @NotNull(message = "Timestamp should not be null.")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "yyyy-MM-dd'T'HH:mm:ssz")
  ZonedDateTime timestamp;

}
