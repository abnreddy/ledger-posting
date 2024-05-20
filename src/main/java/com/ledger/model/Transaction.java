package com.ledger.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "transaction_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    private  String transactionRef;
    private  String transactionType;
    private Date transactionDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountRef", referencedColumnName = "transactionRef")
    private List<TransactionAccount> transactionAccounts;
}
