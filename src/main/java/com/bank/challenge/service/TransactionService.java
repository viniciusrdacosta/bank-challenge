package com.bank.challenge.service;

import com.bank.challenge.model.Transaction;
import com.bank.challenge.model.TransactionStatistics;
import com.bank.challenge.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TransactionService {

  private static final int SIXTY = 60;
  private final TransactionRepository repository;

  @Autowired
  public TransactionService(TransactionRepository repository) {
    this.repository = repository;
  }

  public void add(Transaction transaction) {
    repository.add(transaction);
  }

  public TransactionStatistics getStatisticsBasedOn(Instant timestamp) {
    return repository.statisticsBasedOn(timestamp.minusSeconds(SIXTY).toEpochMilli());
  }

}
