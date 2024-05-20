package com.ledger.ledger.service;

import com.ledger.model.Account;
import com.ledger.model.Transaction;
import com.ledger.model.TransactionAccount;
import com.ledger.model.TransferRequest;
import com.ledger.repository.AccountRepository;
import com.ledger.repository.TransactionAccountRepository;
import com.ledger.repository.TransactionRepository;
import com.ledger.service.TransferService;
import com.ledger.validator.TransferValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    @InjectMocks
    TransferService transferService;

    @Mock
    TransferValidator validator;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionAccountRepository transactionAccountRepository;

    @Mock
    AccountRepository accountRepository;

    @Test
    void transferTest(){
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(Account.builder().build());
        Mockito.doNothing().when(validator).validBalance(Mockito.any());
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(Transaction.builder().build());

        transferService.transferFunds(TransferRequest.builder()
                .transactionRef("T1")
                .transactionType("testing")
                        .transactionAccounts(List.of(TransactionAccount.builder()
                                .accountRef("1")
                                .currency("EUR")
                                .amount(BigDecimal.ONE)
                                .build()))
                .build());
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(validator, Mockito.times(1)).validBalance(Mockito.any());
        Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void findTransactionsTest(){
        Mockito.when(accountRepository.findById(Mockito.anyString())).thenReturn(Optional.of(Account.builder().build()));
        Mockito.when(transactionAccountRepository.findById(Mockito.anyString())).thenReturn(Optional.of(TransactionAccount.builder()
                .transactionRef("test").build()));
        Mockito.when(transactionRepository.findById(Mockito.anyString())).thenReturn(Optional.of(Transaction.builder()
                .transactionRef("test").build()));

        transferService.findTransactionsByAccountRef("1234");
        Mockito.verify(accountRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(transactionAccountRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(transactionRepository, Mockito.times(1)).findById(Mockito.anyString());

    }
}
