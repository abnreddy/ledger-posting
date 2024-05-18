package com.ledger.exception;


public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String accountRef) {
    super("No account found for reference '" + accountRef + "'");
  }
}
