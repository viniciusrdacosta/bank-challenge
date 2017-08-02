package com.bank.challenge.controller;

import com.bank.challenge.controller.model.TransactionRequest;
import com.bank.challenge.domain.TransactionStatistics;
import com.bank.challenge.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static java.time.Instant.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

  public static final double AMOUNT = 100.00;
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private TransactionService service;

  @Test
  public void shouldReturnCreatedStatusWhenTransactionIsCreatedSuccessfully() throws Exception {
    Long transactionDate = now().toEpochMilli();
    TransactionRequest request = new TransactionRequest(AMOUNT, transactionDate);

    mockMvc.perform(post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(APPLICATION_JSON_UTF8))
      .andExpect(status().isCreated());
  }


  @Test
  public void shouldReturnNoContentStatusWhenTransactionIsBeforeSixtySecondsAgo() throws Exception {
    Long transactionDate = now().minusSeconds(61).toEpochMilli();
    TransactionRequest request = new TransactionRequest(AMOUNT, transactionDate);

    mockMvc.perform(post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(APPLICATION_JSON_UTF8))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldReturnNoContentStatusWhenTransactionAmountIsNull() throws Exception {
    Long transactionDate = now().minusSeconds(60).toEpochMilli();
    TransactionRequest request = new TransactionRequest(null, transactionDate);

    mockMvc.perform(post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(APPLICATION_JSON_UTF8))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldReturnNoContentStatusWhenTransactionTimestampIsNull() throws Exception {
    TransactionRequest request = new TransactionRequest(AMOUNT, null);

    mockMvc.perform(post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(APPLICATION_JSON_UTF8))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldReturnTransactionStatistics() throws Exception {
    TransactionStatistics statistics = TransactionStatistics.builder()
      .sum(180.00)
      .avg(90.00)
      .max(120)
      .min(20.00)
      .count(3)
      .build();

    when(service.getStatisticsBasedOn(any(Instant.class))).thenReturn(statistics);

    mockMvc.perform(get("/statistics"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.sum").value("180.0"))
      .andExpect(jsonPath("$.avg").value("90.0"))
      .andExpect(jsonPath("$.max").value("120.0"))
      .andExpect(jsonPath("$.min").value("20.0"))
      .andExpect(jsonPath("$.count").value("3"));
  }

}
