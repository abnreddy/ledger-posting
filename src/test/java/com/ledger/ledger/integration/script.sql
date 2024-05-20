CREATE USER ledger WITH PASSWORD 'ledger';
CREATE DATABASE ledger OWNER ledger;

CREATE TABLE account (
  account_ref VARCHAR(20) NOT NULL,
  amount DECIMAL(20,2) NOT NULL,
  currency VARCHAR(3) NOT NULL,
    PRIMARY KEY(account_ref)
);

GRANT SELECT, INSERT, UPDATE, DELETE
ON account
TO ledger;

CREATE TABLE transaction_history (
  transaction_ref VARCHAR(20) NOT NULL,
  transaction_type VARCHAR(20) NOT NULL,
  transaction_date DATE NOT NULL,

  PRIMARY KEY(transaction_ref)
);

GRANT SELECT, INSERT, UPDATE, DELETE
ON transaction_history
TO ledger;

CREATE TABLE transaction_account(
	transaction_ref VARCHAR(20) NOT NULL,
	account_ref VARCHAR(20) NOT NULL,
	amount DECIMAL(20,2) NOT NULL,
	currency VARCHAR(3) NOT NULL
);

GRANT SELECT, INSERT, UPDATE, DELETE
ON transaction_account
TO ledger;