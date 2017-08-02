package com.bank.challenge.controller;

import com.bank.challenge.controller.model.TransactionRequest;
import com.bank.challenge.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static java.time.ZonedDateTime.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    mockMvc.perform((post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON_UTF8)))
      .andExpect(status().isCreated());
  }


  @Test
  public void shouldReturnNoContentStatusWhenTransactionIsBeforeSixtySecondsAgo() throws Exception {
    TransactionRequest request = new TransactionRequest(BigDecimal.TEN, now().minusSeconds(61));

    mockMvc.perform((post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON_UTF8)))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldReturnNoContentStatusWhenTransactionAmountIsNull() throws Exception {
    TransactionRequest request = new TransactionRequest(null, now());

    mockMvc.perform((post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON_UTF8)))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldReturnNoContentStatusWhenTransactionTimestampIsNull() throws Exception {
    TransactionRequest request = new TransactionRequest(BigDecimal.TEN, null);

    mockMvc.perform((post("/transactions")
      .content(mapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON_UTF8)))
      .andExpect(status().isNoContent());
  }

}
