package main.validation;

import main.to.AccountTO;
import main.to.CalculatedPaymentTO;
import service.Payment;

import java.util.List;
import java.util.Map;

public class AccountValidate {
    public void checkTotalDebtWithTotalCredit(CalculatedPaymentTO calculatedPaymentTO) throws Exception {
        if (calculatedPaymentTO.getTotalCredit() < calculatedPaymentTO.getTotalDebt()) {
            throw new Exception("meghdare hoghoghe karmandan az meghdare kole bedehie sherkat kamtar mibashad");
        }
        if (calculatedPaymentTO.getTotalCredit() > calculatedPaymentTO.getTotalDebt()) {
            throw new Exception("meghdare hughughe karmandan az meghdare kole bedehie sherkat bishtar ast");
        }
    }

    public void checkDebtorBalance(CalculatedPaymentTO calculatedPaymentTO, Map<String, AccountTO> balanceMap) throws Exception {
        AccountTO debtorAcc = balanceMap.get(calculatedPaymentTO.getDebtorAccountNumber());
        if(debtorAcc.getAmount()<calculatedPaymentTO.getTotalCredit()){
            throw new Exception("meghdare mojudi  " + debtorAcc.getAccNumber()+"  kafi nemibashad");
        }


    }
}
