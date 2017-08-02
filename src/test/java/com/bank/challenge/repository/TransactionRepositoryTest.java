package com.bank.challenge.repository;

import com.bank.challenge.domain.Transaction;
import org.junit.Before;
import org.junit.Test;

import static java.math.BigDecimal.TEN;
import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionRepositoryTest {

  private TransactionRepository repository;

  @Before
  public void setUp() {
    repository = new TransactionRepository();
  }

  @Test
  public void shouldAddTransaction() {
    Transaction transaction = Transaction.builder().amount(TEN).timestamp(now()).build();
    Transaction transactionAdded = repository.add(transaction);

    assertThat(transactionAdded.getTransactionId()).isNotNull();
  }

}
