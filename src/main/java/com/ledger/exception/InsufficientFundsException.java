package com.ledger.exception;

public class InsufficientFundsException extends BusinessException {

  public InsufficientFundsException(String accountRef) {
    super("Insufficient funds for '" + accountRef + "'");
  }
}