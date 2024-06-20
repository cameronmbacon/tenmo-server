package com.bacontech.tenmo.services;

import com.bacontech.tenmo.dao.TransferDao;
import com.bacontech.tenmo.exception.InsufficientFundsException;
import com.bacontech.tenmo.exception.TransferNotFoundException;
import com.bacontech.tenmo.model.Transfer;
import com.bacontech.tenmo.model.TransferStatus;
import com.bacontech.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static com.bacontech.tenmo.model.TransferType.*;
import static com.bacontech.tenmo.model.TransferStatus.*;

@Service
public class TransferService {

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private AccountService accountService;

    @Transactional
    public Transfer initiateSendTransfer(int fromAccountId, int toAccountId, BigDecimal amount) throws AccountNotFoundException {
        if (!accountService.hasSufficientFunds(fromAccountId, amount)) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }

        accountService.withdraw(fromAccountId, amount);
        accountService.deposit(toAccountId, amount);

        Transfer transfer = generateTransfer(fromAccountId, toAccountId, amount, SEND, APPROVED);

        return transferDao.saveTransfer(transfer);
    }

    @Transactional
    public Transfer initiateRequestTransfer(int fromAccountId, int toAccountId, BigDecimal amount) {
        Transfer transfer = generateTransfer(fromAccountId, toAccountId, amount, REQUEST, PENDING);

        return transferDao.saveTransfer(transfer);
    }

    @Transactional
    public Transfer acceptRequestTransfer(int transferId) throws AccountNotFoundException {
        Transfer transfer = transferDao.findTransferById(transferId);

        if (transfer == null) {
            throw new TransferNotFoundException("Transfer not found");
        }

        if (!accountService.hasSufficientFunds(transfer.getAccountFrom(), transfer.getAmount())) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }

        accountService.withdraw(transfer.getAccountFrom(), transfer.getAmount());
        accountService.deposit(transfer.getAccountTo(), transfer.getAmount());

        transferDao.updateTransferStatus(transferId, APPROVED);

        return transferDao.findTransferById(transferId);
    }

    @Transactional
    public Transfer rejectRequestTransfer(int transferId) {
        Transfer transfer = transferDao.findTransferById(transferId);

        if (transfer == null) {
            throw new TransferNotFoundException("Transfer not found");
        }

        transferDao.updateTransferStatus(transferId, REJECTED);

        return transferDao.findTransferById(transferId);
    }

    public Transfer getTransferById(int transferId) {
        return transferDao.findTransferById(transferId);
    }

    public List<Transfer> getTransfersByUserId(int userId) {
        return transferDao.findTransfersByUserId(userId);
    }

    private Transfer generateTransfer(int fromAccountId, int toAccountId, BigDecimal amount, TransferType transferType, TransferStatus transferStatus) {
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(fromAccountId);
        transfer.setAccountTo(toAccountId);
        transfer.setAmount(amount);
        transfer.setTransferType(transferType);
        transfer.setTransferStatus(transferStatus);

        return transfer;
    }
}
