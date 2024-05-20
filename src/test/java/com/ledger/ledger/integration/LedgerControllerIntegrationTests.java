package com.ledger.ledger.integration;

import com.ledger.Application;
import com.ledger.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;


@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LedgerControllerIntegrationTests
{
  @LocalServerPort
  private int port;
 
  @Autowired
  private TestRestTemplate restTemplate;
 
  @Test
  public void createAccount() {
    Account employee = Account.builder()
            .accountRef("123")
            .amount(BigDecimal.ONE)
            .currency("GBP")
            .build();
    ResponseEntity<Void> responseEntity = this.restTemplate
      .postForEntity("http://localhost:" + port + "/employees", employee, Void.class);
    //assertEquals(201, responseEntity.getStatusCode().is2xxSuccessful());
  }
}