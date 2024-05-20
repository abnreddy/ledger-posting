package com.ledger.exception;

public class UnbalancedAccountException extends BusinessException {

  public UnbalancedAccountException(String message) {
    super(message);
  }
}