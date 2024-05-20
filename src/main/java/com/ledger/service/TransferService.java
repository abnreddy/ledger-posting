package com.ledger.service;

import com.ledger.exception.AccountNotFoundException;
import com.ledger.exception.InsufficientFundsException;
import com.ledger.model.Account;
import com.ledger.model.Transaction;
import com.ledger.model.TransactionAccount;
import com.ledger.model.TransferRequest;
import com.ledger.repository.AccountRepository;
import com.ledger.repository.TransactionAccountRepository;
import com.ledger.repository.TransactionRepository;
import com.ledger.validator.TransferValidator;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransferService {

    private final TransferValidator validator;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionAccountRepository transactionAccountRepository;

    public TransferService(TransferValidator validator, AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionAccountRepository transactionAccountRepository) {
        this.validator = validator;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionAccountRepository = transactionAccountRepository;
    }

    public void transferFunds(TransferRequest transferRequest) throws InsufficientFundsException, AccountNotFoundException {

        validateRequest(transferRequest);
        for (TransactionAccount transactionAccount : transferRequest.getTransactionAccounts()) {
            accountRepository.save(Account.builder()
                            .amount(transactionAccount.getAmount())
                            .currency(transactionAccount.getCurrency())
                            .accountRef(transactionAccount.getAccountRef())
                    .build());
        }
        validator.validBalance(transferRequest.getTransactionAccounts());
        storeTransaction(transferRequest);
    }

    private void validateRequest(TransferRequest request) {
        validator.validateTransferRequest(request);
        validator.isTransactionBalanced(request.getTransactionAccounts());
        validator.transferRequestExists(request.getTransactionRef());
        validator.currenciesMatch(request.getTransactionAccounts());
    }

    private void storeTransaction(TransferRequest request) {
        Transaction transaction = new Transaction(
                request.getTransactionRef(),
                request.getTransactionType(),
                new Date(),
                request.getTransactionAccounts()
        );
        transactionRepository.save(transaction);
    }

    public Transaction findTransactionsByAccountRef(String accountRef)
            throws AccountNotFoundException {
        if (accountRepository.findById(accountRef).isEmpty()) {
            throw new AccountNotFoundException(accountRef);
        }
        TransactionAccount transactionAccount = transactionAccountRepository.findById(accountRef).orElse(TransactionAccount.builder().build());
        if (transactionAccount.getTransactionRef() == null) {
            return Transaction.builder().build();
        }

        return transactionRepository.findById(transactionAccount.getTransactionRef()).orElse(Transaction.builder().build());
    }

}
