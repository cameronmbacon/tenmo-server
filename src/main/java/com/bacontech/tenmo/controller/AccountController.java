package com.bacontech.tenmo.controller;


import com.bacontech.tenmo.model.Account;
import com.bacontech.tenmo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/tenmo/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {this.accountService = accountService;}

    @RequestMapping(path = "/accountId/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> findAccountById(@PathVariable int id) {
        Account account = accountService.findAccountById(id);
        return ResponseEntity.ok(account);
    }

    @RequestMapping(path = "/userId/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> findAccountByUserId(@PathVariable int id) {
        Account account = accountService.findAccountByUserId(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/userId/{userId}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable int userId) throws AccountNotFoundException {
        BigDecimal balance = accountService.retrieveAccountBalance(userId);
        return ResponseEntity.ok(balance);
    }
}
