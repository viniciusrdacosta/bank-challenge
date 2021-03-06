package com.bank.challenge.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TransactionStatistics {

  double sum;
  double avg;
  double max;
  double min;
  long count;

}
