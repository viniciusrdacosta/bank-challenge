package com.bank.challenge.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.UUID;

@Value
@Wither
@Builder
public class Transaction {

  UUID transactionId;
  Double amount;
  Long timestamp;

}
