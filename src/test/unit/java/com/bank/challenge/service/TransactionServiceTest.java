package com.bank.challenge.service;

import com.bank.challenge.model.Transaction;
import com.bank.challenge.model.TransactionStatistics;
import com.bank.challenge.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

  @Mock
  private TransactionRepository repository;

  private TransactionService service;

  @Before
  public void setUp() {
    service = new TransactionService(repository);
  }

  @Test
  public void shouldAddTransaction() {
    Transaction transaction = Transaction.builder()
      .amount(100.00)
      .timestamp(now().toEpochMilli())
      .build();

    when(repository.add(eq(transaction))).thenReturn(transaction.withTransactionId(randomUUID()));

    service.add(transaction);

    verify(repository).add(eq(transaction));
  }

  @Test
  public void shouldGetStatisticsForGivenDate() {
    Instant currentDate = now();
    Long validDateTransaction = currentDate.minusSeconds(60).toEpochMilli();

    TransactionStatistics expectedStatistics = TransactionStatistics.builder()
      .sum(120.00)
      .avg(60.00)
      .max(100.00)
      .min(20.00)
      .count(2)
      .build();

    when(repository.statisticsBasedOn(eq(validDateTransaction))).thenReturn(expectedStatistics);

    TransactionStatistics actualStatistics = service.getStatisticsBasedOn(currentDate);

    verify(repository).statisticsBasedOn(eq(validDateTransaction));
    assertThat(actualStatistics).isEqualTo(expectedStatistics);

  }
}
