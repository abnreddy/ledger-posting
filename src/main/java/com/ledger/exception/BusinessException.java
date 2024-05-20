package com.ledger.exception;

public abstract class BusinessException extends RuntimeException {

  protected BusinessException(String message) {
    super(message);
  }
}