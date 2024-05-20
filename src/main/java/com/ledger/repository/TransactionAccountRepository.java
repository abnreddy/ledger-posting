package com.ledger.repository;

import com.ledger.model.TransactionAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionAccountRepository extends JpaRepository<TransactionAccount, String> {
}
