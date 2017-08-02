package com.bank.challenge.repository;

import com.bank.challenge.domain.Transaction;
import com.bank.challenge.domain.TransactionStatistics;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionRepositoryTest {

  private TransactionRepository repository;

  @Before
  public void setUp() {
    repository = new TransactionRepository();
  }

  @Test
  public void shouldAddTransaction() {
    Long transactionDate = now().toEpochMilli();
    Transaction transaction = transactionWith(100.00, transactionDate);
    Transaction transactionAdded = repository.add(transaction);

    assertThat(transactionAdded.getTransactionId()).isNotNull();
  }

  @Test
  public void shouldGetTransactionsStatisticsForGivenDate() {
    Instant transactionDate = now();

    repository.add(transactionWith(20.00, transactionDate.toEpochMilli()));
    repository.add(transactionWith(120.00, transactionDate.plusSeconds(1).toEpochMilli()));
    repository.add(transactionWith(10.22, transactionDate.minusSeconds(1).toEpochMilli()));

    TransactionStatistics actualStatistics = repository.statisticsBasedOn(transactionDate.toEpochMilli());
    TransactionStatistics expectedStatistics = TransactionStatistics.builder()
      .sum(140.00)
      .avg(70.00)
      .max(120.00)
      .min(20.00)
      .count(2)
      .build();

    assertThat(actualStatistics).isEqualTo(expectedStatistics);
  }

  private Transaction transactionWith(Double amount, Long timestamp) {
    return Transaction.builder().amount(amount).timestamp(timestamp).build();
  }
}
