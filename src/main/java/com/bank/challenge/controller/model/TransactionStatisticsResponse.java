package com.bank.challenge.controller.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TransactionStatisticsResponse {

  double sum;
  double avg;
  double max;
  double min;
  long count;

}
