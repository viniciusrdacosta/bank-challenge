package com.bank.challenge.service;

import com.bank.challenge.domain.Transaction;
import com.bank.challenge.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final TransactionRepository repository;

  public TransactionService(TransactionRepository repository) {
    this.repository = repository;
  }

  public void add(Transaction transaction) {
    repository.add(transaction);
  }

}
