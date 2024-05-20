package com.ledger.ledger.controller;

import com.ledger.controller.LedgerController;
import com.ledger.model.Account;
import com.ledger.model.Transaction;
import com.ledger.model.TransactionAccount;
import com.ledger.model.TransferRequest;
import com.ledger.service.AccountService;
import com.ledger.service.TransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LedgerControllerTest {

    @InjectMocks
    LedgerController ledgerController;

    @Mock
    AccountService accountService;

    @Mock
    TransferService transferService;

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

    @Test
    void transferTest(){
        Mockito.doNothing().when(transferService).transferFunds(Mockito.any());
        ledgerController.transfer(TransferRequest.builder()
                .transactionType("USD")
                .transactionRef("1234")
                        .transactionAccounts(List.of(TransactionAccount.builder()
                        .accountRef("1")
                        .currency("EUR")
                        .amount(BigDecimal.ONE)
                        .build()))
                .build());
        Mockito.verify(transferService, Mockito.times(1)).transferFunds(Mockito.any());
    }

    @Test
    void findTransactionTest(){
        Mockito.when(transferService.findTransactionsByAccountRef(Mockito.anyString())).thenReturn(Transaction.builder().build());
        ledgerController.findTransaction("1234");
        Mockito.verify(transferService, Mockito.times(1)).findTransactionsByAccountRef("1234");
    }
}
