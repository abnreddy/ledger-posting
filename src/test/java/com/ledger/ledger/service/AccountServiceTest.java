package com.ledger.ledger.service;

import com.ledger.model.Account;
import com.ledger.repository.AccountRepository;
import com.ledger.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Test
    void createAccountTest(){
        Mockito.when(accountRepository.getReferenceById(Mockito.anyString())).thenReturn(Account.builder().build());
        accountService.createAccount(Account.builder()
                        .currency("USD")
                        .accountRef("1234")
                        .amount(BigDecimal.ONE)
                        .build());
        Mockito.verify(accountRepository, Mockito.times(1)).getReferenceById("1234");
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void retrieveAccountTest(){
        Mockito.when(accountRepository.getReferenceById(Mockito.anyString())).thenReturn(Account.builder().build());
        accountService.getAccountBalance("1234");
        Mockito.verify(accountRepository, Mockito.times(1)).getReferenceById("1234");
    }
}
