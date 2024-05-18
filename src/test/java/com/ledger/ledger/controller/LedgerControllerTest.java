package com.ledger.ledger.controller;

import com.ledger.controller.LedgerController;
import com.ledger.model.Account;
import com.ledger.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class LedgerControllerTest {

    @InjectMocks
    LedgerController ledgerController;

    @Mock
    AccountService accountService;

    @Test
    void createAccountTest(){
        Mockito.doNothing().when(accountService).createAccount(Mockito.any());
        ledgerController.createAccount(Account.builder()
                .currency("USD")
                .accountRef("1234")
                .amount(BigDecimal.ONE)
                .build());
        Mockito.verify(accountService, Mockito.times(1)).createAccount(Mockito.any());
    }

    @Test
    void retrieveAccountTest(){
        Mockito.when(accountService.getAccountBalance(Mockito.anyString())).thenReturn(Account.builder().build());
        ledgerController.retrieveAccount("1234");
        Mockito.verify(accountService, Mockito.times(1)).getAccountBalance("1234");
    }
}
