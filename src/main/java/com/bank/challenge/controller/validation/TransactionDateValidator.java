package com.bank.challenge.controller.validation;

import com.bank.challenge.controller.model.TransactionRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.ZonedDateTime;

public class TransactionDateValidator implements ConstraintValidator<NotBeforeSixtySecondsAgo, TransactionRequest> {

  private static final int SIXTY = 60;

  @Override
  public void initialize(NotBeforeSixtySecondsAgo constraintAnnotation) {
  }

  @Override
  public boolean isValid(TransactionRequest value, ConstraintValidatorContext context) {
    ZonedDateTime validTimestamp = value.getRequestDate().minusSeconds(SIXTY);
    ZonedDateTime transactionTimeStamp = value.getTimestamp();

    return transactionTimeStamp == null || validTimestamp.isEqual(transactionTimeStamp) || validTimestamp.isBefore(transactionTimeStamp);
  }
}
