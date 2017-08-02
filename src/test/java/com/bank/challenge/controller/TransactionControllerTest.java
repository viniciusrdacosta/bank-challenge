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

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private TransactionService service;

  @Test
  public void shouldReturnCreatedStatusWhenTransactionIsCreatedSuccessfully() throws Exception {
    TransactionRequest request = new TransactionRequest(BigDecimal.TEN, now());

    mockMvc.perform(post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(APPLICATION_JSON_UTF8))
      .andExpect(status().isCreated());
  }


  @Test
  public void shouldReturnNoContentStatusWhenTransactionIsBeforeSixtySecondsAgo() throws Exception {
    TransactionRequest request = new TransactionRequest(BigDecimal.TEN, now().minusSeconds(61));

    mockMvc.perform(post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(APPLICATION_JSON_UTF8))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldReturnNoContentStatusWhenTransactionAmountIsNull() throws Exception {
    TransactionRequest request = new TransactionRequest(null, now());

    mockMvc.perform(post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(APPLICATION_JSON_UTF8))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldReturnNoContentStatusWhenTransactionTimestampIsNull() throws Exception {
    TransactionRequest request = new TransactionRequest(BigDecimal.TEN, null);

    mockMvc.perform(post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(APPLICATION_JSON_UTF8))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldReturnTransactionStatistics() throws Exception {
    TransactionStatistics statistics = TransactionStatistics.builder()
      .sum(120.00)
      .avg(60.00)
      .max(100.00)
      .min(20.00)
      .count(2)
      .build();

    when(service.getStatisticsBasedOn(any(ZonedDateTime.class))).thenReturn(statistics);

    mockMvc.perform(get("/statistics"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(APPLICATION_JSON_UTF8))
      .andExpect(jsonPath("$.sum").value("120.0"))
      .andExpect(jsonPath("$.avg").value("60.0"))
      .andExpect(jsonPath("$.max").value("100.0"))
      .andExpect(jsonPath("$.min").value("20.0"))
      .andExpect(jsonPath("$.count").value("2"));
  }

}
