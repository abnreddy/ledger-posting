package com.ledger.exception;

public class InfrastructureException extends RuntimeException {

  public InfrastructureException(String message) {
    super(message);
  }

  public InfrastructureException(Throwable cause) {
    super(cause);
  }
}
