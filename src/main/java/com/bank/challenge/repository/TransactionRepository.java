package com.bank.challenge.repository;

import com.bank.challenge.model.Transaction;
import com.bank.challenge.model.TransactionStatistics;
import org.springframework.stereotype.Repository;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

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

  public TransactionStatistics statisticsBasedOn(Long timestamp) {
    DoubleSummaryStatistics statistics = transactions.stream()
      .filter(createdBeforeSixtySecondsFrom(timestamp))
      .mapToDouble(Transaction::getAmount).summaryStatistics();

    return TransactionStatistics.builder()
      .avg(statistics.getAverage())
      .min(statistics.getMin())
      .sum(statistics.getSum())
      .max(statistics.getMax())
      .count(statistics.getCount())
      .build();
  }

  private Predicate<Transaction> createdBeforeSixtySecondsFrom(Long timestamp) {
    return transaction -> timestamp <= transaction.getTimestamp();
  }
}
