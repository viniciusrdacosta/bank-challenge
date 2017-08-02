package com.bank.challenge.controller.validation;

import com.bank.challenge.controller.model.TransactionRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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

    ZonedDateTime validTimestamp = value.getRequestDate().minusSeconds(SIXTY).truncatedTo(ChronoUnit.SECONDS);
    ZonedDateTime transactionTimeStamp = value.getTimestamp().truncatedTo(ChronoUnit.SECONDS);

    return validTimestamp.isEqual(transactionTimeStamp) || validTimestamp.isBefore(transactionTimeStamp);
  }
}
