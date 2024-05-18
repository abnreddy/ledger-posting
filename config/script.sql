CREATE USER ledger WITH PASSWORD 'ledger';
CREATE DATABASE ledger OWNER ledger;

CREATE TABLE account (
  account_ref VARCHAR(20) NOT NULL,
  amount DECIMAL(20,2) NOT NULL,
  currency VARCHAR(3) NOT NULL
);

GRANT SELECT, INSERT, UPDATE, DELETE
ON account
TO ledger;