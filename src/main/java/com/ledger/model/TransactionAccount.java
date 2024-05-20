package com.ledger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionAccount {

  @Id
  private String accountRef;
  private String transactionRef;
  private String currency;
  private BigDecimal amount;
}