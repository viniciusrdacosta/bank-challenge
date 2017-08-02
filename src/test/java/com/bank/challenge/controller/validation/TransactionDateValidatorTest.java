package com.bank.challenge.controller.validation;

import com.bank.challenge.controller.model.TransactionRequest;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDateValidatorTest {

  private static final double ANY_AMOUNT = 100.00;
  private TransactionDateValidator validator;

  @Before
  public void setUp() throws Exception {
    validator = new TransactionDateValidator();
  }

  @Test
  public void shouldBeValidWhenTransactionTimestampIsExactlySixtySecondsAgo() {
    Long transactionDate = Instant.now().minusSeconds(60).toEpochMilli();
    TransactionRequest request = new TransactionRequest(ANY_AMOUNT, transactionDate);

    assertThat(validator.isValid(request, null)).isTrue();
  }

  @Test
  public void shouldBeValidWhenTransactionTimestampIsAfterSixtySecondsAgo() {
    Long transactionDate = Instant.now().plusSeconds(1).toEpochMilli();
    TransactionRequest request = new TransactionRequest(ANY_AMOUNT, transactionDate);

    assertThat(validator.isValid(request, null)).isTrue();
  }

  @Test
  public void shouldBeInvalidWhenTransactionTimestampIsBeforeSixtySecondsAgo() {
    Long transactionDate = Instant.now().minusSeconds(61).toEpochMilli();
    TransactionRequest request = new TransactionRequest(ANY_AMOUNT, transactionDate);

    assertThat(validator.isValid(request, null)).isFalse();
  }

}
