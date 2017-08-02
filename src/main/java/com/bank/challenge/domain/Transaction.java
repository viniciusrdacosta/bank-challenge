package com.bank.challenge.domain;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Value
@Wither
@Builder
public class Transaction {

  UUID transactionId;
  BigDecimal amount;
  ZonedDateTime timestamp;

}
