package com.ledger.controller;

import com.ledger.exception.InfrastructureException;
import com.ledger.model.Account;
import com.ledger.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ledger")
@Slf4j
@RestController
public class LedgerController {

    private final AccountService accountService;

    public LedgerController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/account")
    public ResponseEntity<Void> createAccount(@RequestBody Account account) {
        try{
            accountService.createAccount(account);
        } catch (InfrastructureException e){
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountRef}")
    public Account retrieveAccount(@PathVariable("accountRef") String accountRef) {

        return accountService.getAccountBalance(String.valueOf(accountRef));
    }
}
