package com.bacontech.tenmo.dao;

import com.bacontech.tenmo.model.Transfer;
import com.bacontech.tenmo.model.TransferStatus;

import java.util.List;

public interface TransferDao {

    Transfer findTransferById(int id);

    List<Transfer> findTransfersByUserId(int userId);

    Transfer saveTransfer(Transfer transfer);

    void updateTransferStatus(int transferId, TransferStatus status);
}
