package com.bank.challenge.repository;

import com.bank.challenge.domain.Transaction;
import com.bank.challenge.domain.TransactionStatistics;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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
    Transaction transaction = transactionWith(BigDecimal.TEN, now().truncatedTo(ChronoUnit.SECONDS));
    Transaction transactionAdded = repository.add(transaction);

    assertThat(transactionAdded.getTransactionId()).isNotNull();
  }

  @Test
  public void shouldGetTransactionsStatisticsForGivenDate() {
    ZonedDateTime dateCriteria = now().truncatedTo(ChronoUnit.SECONDS);

    repository.add(transactionWith(BigDecimal.valueOf(20), dateCriteria));
    repository.add(transactionWith(BigDecimal.valueOf(100.00), dateCriteria.plusSeconds(1)));
    repository.add(transactionWith(BigDecimal.valueOf(10.22), dateCriteria.minusSeconds(1)));

    TransactionStatistics actualStatistics = repository.statisticsBasedOn(dateCriteria);
    TransactionStatistics expectedStatistics = TransactionStatistics.builder()
      .sum(120.00)
      .avg(60.00)
      .max(100.00)
      .min(20.00)
      .count(2)
      .build();

    assertThat(actualStatistics).isEqualTo(expectedStatistics);
  }

  private Transaction transactionWith(BigDecimal amount, ZonedDateTime timestamp) {
    return Transaction.builder().amount(amount).timestamp(timestamp).build();
  }
}
