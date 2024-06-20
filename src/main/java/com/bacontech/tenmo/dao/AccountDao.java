package com.bacontech.tenmo.dao;

import com.bacontech.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account findAccountById(int id);

    Account findAccountByUserId(int userId);

    Account retrieveAccountBalanceByUserId(int userId);

    void updateBalance(int accountId, BigDecimal balance);
}
