package com.ledger.service;

import com.ledger.exception.AccountNotFoundException;
import com.ledger.exception.InfrastructureException;
import com.ledger.model.Account;
import com.ledger.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(Account account) throws InfrastructureException {

        if(this.getAccountBalance(account.getAccountRef()).getAccountRef() == null){
            accountRepository.save(Account.builder()
                    .currency(account.getCurrency())
                    .accountRef(account.getAccountRef()).amount(account.getAmount())
                    .build());
        } else {
             throw new InfrastructureException("Account Already exists");
        }

    }

    public Account getAccountBalance(String accountRef) throws AccountNotFoundException {
        return accountRepository.findById(accountRef).orElse(Account.builder().build());
    }
}
