package com.ledger.exception;

public class TransferRequestExistsException extends BusinessException {

  public TransferRequestExistsException(String transactionRef) {
    super("Transfer request already exists for reference '" + transactionRef + "'");
  }
}