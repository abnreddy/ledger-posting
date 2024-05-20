package com.ledger.validator;

import com.ledger.exception.*;
import com.ledger.model.Account;
import com.ledger.model.TransactionAccount;
import com.ledger.model.TransferRequest;
import com.ledger.repository.AccountRepository;
import com.ledger.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class TransferValidator {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferValidator(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void validateTransferRequest(TransferRequest transferRequest) {
        if (transferRequest.getTransactionRef() == null
                ||
                transferRequest.getTransactionType() == null
                ||
                transferRequest.getTransactionAccounts().size() < 2) {
            throw new IllegalArgumentException("Incomplete transaction request");
        }
    }

    public void transferRequestExists(String transactionRef) {
        if (transactionRepository.findById(transactionRef).isPresent()) {
            throw new TransferRequestExistsException(transactionRef);
        }
    }

    public void isTransactionBalanced(Iterable<TransactionAccount> transactionAccounts) throws UnbalancedAccountException {
        Map<String, List<TransactionAccount>> accountsByCurrency = sortByCurrency(transactionAccounts);
        for (String currency : accountsByCurrency.keySet()) {
            checkTransactionAccountBalanced(accountsByCurrency.get(currency));
        }
    }

    public void currenciesMatch(Iterable<TransactionAccount> transactionAccounts)
            throws TransferValidationException, AccountNotFoundException {
        for (TransactionAccount transactionAccount : transactionAccounts) {
            Account account = accountRepository.findById(transactionAccount.getAccountRef()).orElse(Account.builder().build());
            if (account.getAccountRef() == null) {
                throw new AccountNotFoundException(transactionAccount.getAccountRef());
            }
            if (!account.getCurrency().equals(transactionAccount.getCurrency())) {
                throw new TransferValidationException(
                        "Currency from transaction account does not match account's currency for one or more accounts");
            }
        }
    }

    public void validBalance(Iterable<TransactionAccount> transactionAccounts) throws InsufficientFundsException {
        for (String accountRef : getAccountRefs(transactionAccounts)) {
            Account account = accountRepository.getReferenceById(accountRef);
            if (account.isOverdrawn()) {
                throw new InsufficientFundsException(accountRef);
            }
        }
    }

    private void checkTransactionAccountBalanced(Iterable<TransactionAccount> transactionAccounts)
            throws UnbalancedAccountException {
        BigDecimal sum = BigDecimal.ZERO;
        for (TransactionAccount transactionAccount : transactionAccounts) {
            sum = sum.add(transactionAccount.getAmount());
        }
    }

    private Map<String, List<TransactionAccount>> sortByCurrency(Iterable<TransactionAccount> transactionAccounts) {
        Map<String, List<TransactionAccount>> legsByCurrency = new HashMap<>();
        for (TransactionAccount transactionAccount : transactionAccounts) {
            legsByCurrency.put(transactionAccount.getCurrency(), List.of(transactionAccount));
        }
        return legsByCurrency;
    }

    private Set<String> getAccountRefs(Iterable<TransactionAccount> transactionAccounts) {
        Set<String> accounts = new HashSet<>();
        for (TransactionAccount transactionAccount : transactionAccounts) {
            accounts.add(transactionAccount.getAccountRef());
        }
        return accounts;
    }

}
