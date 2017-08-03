package com.bank.challenge;

import com.jayway.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.JsonConfig.jsonConfig;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class TransactionEndToEndTest {

  private static final String TRANSACTIONS_ENDPOINT = "/transactions";

  @LocalServerPort
  private int serverPort;

  @Test
  public void shouldReturn201StatusWhenTransactionIsCreatedSuccessfully() {
    String requestBody = transactionWith(100.00, Instant.now().toEpochMilli());
    given()
      .port(serverPort)
      .body(requestBody)
      .contentType(JSON)
      .when()
      .post(TRANSACTIONS_ENDPOINT)
      .then()
      .statusCode(HttpStatus.CREATED.value());
  }

  @Test
  public void shouldReturn204StatusWhenTransactionIsInvalid() {
    String requestBody = transactionWith(100.00, Instant.now().minusSeconds(61).toEpochMilli());
    given()
      .port(serverPort)
      .body(requestBody)
      .contentType(JSON)
      .when()
      .post(TRANSACTIONS_ENDPOINT)
      .then()
      .statusCode(HttpStatus.NO_CONTENT.value());
  }

  @Test
  public void shouldReturnTransactionStatisticsFrom60SecondsAgo() {
    given().port(serverPort).body(transactionWith(100.00, Instant.now().toEpochMilli())).contentType(JSON).post(TRANSACTIONS_ENDPOINT);
    given().port(serverPort).body(transactionWith(60.00, Instant.now().minusSeconds(20).toEpochMilli())).contentType(JSON).post(TRANSACTIONS_ENDPOINT);
    given().port(serverPort).body(transactionWith(200.00, Instant.now().minusSeconds(60).toEpochMilli())).contentType(JSON).post(TRANSACTIONS_ENDPOINT);

    given()
      .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE)))
      .port(serverPort)
      .when()
      .get("/statistics")
      .then()
      .statusCode(HttpStatus.OK.value())
      .body("sum", equalTo(160.0))
      .body("avg", equalTo(80.0))
      .body("max", equalTo(100.0))
      .body("min", equalTo(60.0))
      .body("count", equalTo(2));
  }

  private String transactionWith(Double amount, Long timestamp) {
    return new StringBuilder()
      .append("{\"amount\": ")
      .append(amount)
      .append(", \"timestamp\": ")
      .append(timestamp)
      .append(" }")
      .toString();
  }

}

