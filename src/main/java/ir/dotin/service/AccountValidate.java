package ir.dotin.service;

import ir.dotin.dto.AccountDTO;
import ir.dotin.dto.CalculatedPaymentDTO;
import ir.dotin.exception.NoSameAccountInBalanceException;
import ir.dotin.exception.NotEnoughBalanceException;
import ir.dotin.exception.NotEqualTotalDebtWithTotalCredit;

import java.util.List;
import java.util.Map;

public class AccountValidate {
    public void checkTotalDebtWithTotalCredit(CalculatedPaymentDTO calculatedPaymentDTO) throws Exception {
        if (!calculatedPaymentDTO.getTotalCredit().equals(calculatedPaymentDTO.getTotalDebt())) {
            throw new NotEqualTotalDebtWithTotalCredit(calculatedPaymentDTO.getTotalDebt(), calculatedPaymentDTO.getTotalCredit());
        }

    }

    public void checkDebtorBalance(CalculatedPaymentDTO calculatedPaymentDTO, Map<String, AccountDTO> balanceMap) throws Exception {
        if (!balanceMap.containsKey(calculatedPaymentDTO.getDebtorAccountNumber())) {
        }
        AccountDTO debtorAcc = balanceMap.get(calculatedPaymentDTO.getDebtorAccountNumber());
        if (debtorAcc.getAmount() < calculatedPaymentDTO.getTotalCredit()) {
            throw new NotEnoughBalanceException(debtorAcc.getAccNumber(), debtorAcc.getAmount(), calculatedPaymentDTO.getTotalDebt());
        }
    }

    public void checkCreditorAccInBalanceFile(List<AccountDTO> paymentList, Map<String, AccountDTO> balanceMap) throws Exception {
        for (AccountDTO paymentTO : paymentList) {
            if (paymentTO.getType().equals("creditor")) {
                checkAccountInBalanceFile(balanceMap, paymentTO);
            }
        }
    }

    public void checkAccountInBalanceFile(Map<String, AccountDTO> balanceMap, AccountDTO paymentTO) throws NoSameAccountInBalanceException {
        if (!balanceMap.containsKey(paymentTO.getAccNumber())) {
            throw new NoSameAccountInBalanceException(paymentTO.getAccNumber());
        }
    }
}

