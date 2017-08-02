package com.bank.challenge.controller.validation;

import com.bank.challenge.controller.model.TransactionRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.time.Instant.ofEpochMilli;

public class TransactionDateValidator implements ConstraintValidator<NotBeforeSixtySecondsAgo, TransactionRequest> {

  private static final int SIXTY = 60;

  @Override
  public void initialize(NotBeforeSixtySecondsAgo constraintAnnotation) {
  }

  @Override
  public boolean isValid(TransactionRequest value, ConstraintValidatorContext context) {
    if (value.getTimestamp() == null) {
      return true;
    }

    Long validTransactionTimestamp = ofEpochMilli(value.getRequestDateTimestamp()).minusSeconds(SIXTY).toEpochMilli();
    Long transactionTimestamp = value.getTimestamp();

    return validTransactionTimestamp <= transactionTimestamp;
  }
}
