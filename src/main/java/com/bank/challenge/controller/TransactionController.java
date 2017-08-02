package com.bank.challenge.controller;

import com.bank.challenge.controller.model.TransactionRequest;
import com.bank.challenge.domain.Transaction;
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

@Slf4j
@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {

  private final TransactionService service;

  @Autowired
  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public void add(@Valid @RequestBody TransactionRequest request) {
    Transaction transaction = Transaction.builder().amount(request.getAmount()).timestamp(request.getTimestamp()).build();
    service.add(transaction);
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
