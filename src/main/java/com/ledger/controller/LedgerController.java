package com.ledger.controller;

import com.ledger.exception.InfrastructureException;
import com.ledger.model.Account;
import com.ledger.model.Transaction;
import com.ledger.model.TransferRequest;
import com.ledger.service.AccountService;
import com.ledger.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ledger")
@Slf4j
@RestController
public class LedgerController {

    private final AccountService accountService;
    private final TransferService transferService;


    public LedgerController(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }


    @PostMapping("/create-account")
    @Async
    public ResponseEntity<Void> createAccount(@RequestBody Account account) {
        try{
            accountService.createAccount(account);
        } catch (InfrastructureException e){
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    @Async
    public ResponseEntity<Void> transfer(@RequestBody TransferRequest transferRequest) {
        try{
            transferService.transferFunds(transferRequest);
        } catch (InfrastructureException e){
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/retrieve-account/{accountRef}")
    public Account retrieveAccount(@PathVariable("accountRef") String accountRef) {

        return accountService.getAccountBalance(String.valueOf(accountRef));
    }

    @GetMapping("/transaction/{accountRef}")
    public Transaction findTransaction(@PathVariable("accountRef") String accountRef) {

        return transferService.findTransactionsByAccountRef(accountRef);
    }
}
