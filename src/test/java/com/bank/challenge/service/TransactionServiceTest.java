package com.bank.challenge.service;

import com.bank.challenge.domain.Transaction;
import com.bank.challenge.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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
}
