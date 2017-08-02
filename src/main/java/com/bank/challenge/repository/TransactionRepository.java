package com.bank.challenge.repository;

import com.bank.challenge.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TransactionRepository {

  private final List<Transaction> transactions;

  public TransactionRepository() {
    this.transactions = new CopyOnWriteArrayList<>();
  }

  public Transaction add(Transaction transaction) {
    Transaction transactionToAdd = transaction.withTransactionId(UUID.randomUUID());
    transactions.add(transactionToAdd);
    return transactionToAdd;
  }

}
