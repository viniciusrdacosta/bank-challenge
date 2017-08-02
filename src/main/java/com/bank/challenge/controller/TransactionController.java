package com.bank.challenge.controller;

import com.bank.challenge.controller.model.TransactionRequest;
import com.bank.challenge.controller.model.TransactionStatisticsResponse;
import com.bank.challenge.domain.Transaction;
import com.bank.challenge.domain.TransactionStatistics;
import com.bank.challenge.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.time.Instant.now;

@Slf4j
@RestController
public class TransactionController {

  private final TransactionService service;

  @Autowired
  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @PostMapping(path = "/transactions")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void add(@Valid @RequestBody TransactionRequest request) {
    Transaction transaction = Transaction.builder().amount(request.getAmount()).timestamp(request.getTimestamp()).build();
    service.add(transaction);
  }

  @GetMapping(path = "/statistics")
  @ResponseStatus(code = HttpStatus.OK)
  public TransactionStatisticsResponse statistics() {
    TransactionStatistics statistics = service.getStatisticsBasedOn(now());

    return TransactionStatisticsResponse.builder()
      .sum(statistics.getSum())
      .avg(statistics.getAvg())
      .max(statistics.getMax())
      .min(statistics.getMin())
      .count(statistics.getCount())
      .build();
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void handleException(MethodArgumentNotValidException exception) {
    logErrors(exception);
  }

  private void logErrors(MethodArgumentNotValidException exception) {
    log(exception.getBindingResult().getGlobalErrors());
    log(exception.getBindingResult().getFieldErrors());
  }

  private void log(List<? extends ObjectError> errors) {
    errors.stream()
      .map(DefaultMessageSourceResolvable::getDefaultMessage)
      .filter(message -> !message.isEmpty())
      .forEach(log::error);
  }

}
