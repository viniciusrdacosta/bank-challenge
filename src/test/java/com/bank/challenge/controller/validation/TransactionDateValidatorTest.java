package com.bank.challenge.controller.validation;

import com.bank.challenge.controller.model.TransactionRequest;
import org.junit.Test;

import java.math.BigDecimal;

import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDateValidatorTest {

  @Test
  public void shouldBeValidWhenTransactionTimestampIsExactlySixtySecondsAgo() {
    TransactionDateValidator validator = new TransactionDateValidator();
    TransactionRequest request = new TransactionRequest(BigDecimal.TEN, now().minusSeconds(60));

    assertThat(validator.isValid(request, null)).isTrue();
  }

  @Test
  public void shouldBeValidWhenTransactionTimestampIsAfterSixtySecondsAgo() {
    TransactionDateValidator validator = new TransactionDateValidator();
    TransactionRequest request = new TransactionRequest(BigDecimal.TEN, now());

    assertThat(validator.isValid(request, null)).isTrue();
  }

  @Test
  public void shouldBeInvalidWhenTransactionTimestampIsBeforeSixtySecondsAgo() {
    TransactionDateValidator validator = new TransactionDateValidator();
    TransactionRequest request = new TransactionRequest(BigDecimal.TEN, now().minusSeconds(61));

    assertThat(validator.isValid(request, null)).isFalse();
  }

}
