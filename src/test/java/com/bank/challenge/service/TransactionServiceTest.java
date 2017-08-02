package com.bank.challenge.service;

import com.bank.challenge.domain.Transaction;
import com.bank.challenge.domain.TransactionStatistics;
import com.bank.challenge.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static java.time.ZonedDateTime.now;
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
    Transaction transaction = Transaction.builder().amount(BigDecimal.TEN).timestamp(ZonedDateTime.now()).build();

    when(repository.add(eq(transaction))).thenReturn(transaction.withTransactionId(UUID.randomUUID()));

    service.add(transaction);

    verify(repository).add(eq(transaction));
  }

  @Test
  public void shouldGetStatisticsForGivenDate() {
    ZonedDateTime currentDate = now().truncatedTo(ChronoUnit.SECONDS);
    ZonedDateTime validDateTransaction = currentDate.minusSeconds(60);

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
