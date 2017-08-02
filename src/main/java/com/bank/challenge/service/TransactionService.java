package com.bank.challenge.service;

import com.bank.challenge.domain.Transaction;
import com.bank.challenge.domain.TransactionStatistics;
import com.bank.challenge.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TransactionService {

  private final TransactionRepository repository;

  @Autowired
  public TransactionService(TransactionRepository repository) {
    this.repository = repository;
  }

  public void add(Transaction transaction) {
    repository.add(transaction);
  }

  public TransactionStatistics getStatisticsBasedOn(ZonedDateTime timestamp) {
    ZonedDateTime validTransactionDate = timestamp.minusSeconds(60).truncatedTo(ChronoUnit.SECONDS);
    return repository.statisticsBasedOn(validTransactionDate);
  }

}
