package main.validation;

import main.to.AccountTO;
import main.to.CalculatedPaymentTO;

import java.util.List;

public class AccountValidate {
    public void checkTotalDebtWithTotalCredit(CalculatedPaymentTO calculatedPaymentTO) throws Exception {
        if (calculatedPaymentTO.getTotalCredit() < calculatedPaymentTO.getTotalDebt()) {
            throw new Exception("meghdare hoghoghe karmandan az meghdare kole bedehie sherkat kamtar mibashad");
        }
        if (calculatedPaymentTO.getTotalCredit() > calculatedPaymentTO.getTotalDebt()) {
            throw new Exception("meghdare hughughe karmandan az meghdare kole bedehie sherkat bishtar ast");
        }
    }

    public void checkDebtorBalance(CalculatedPaymentTO calculatedPaymentTO, List<AccountTO> balanceList) throws Exception {
        //peymayeshe kole acc ha dar balance list be manzore peyda kardane deptor balance
        for (AccountTO accountTO : balanceList) {
            //check accountTO.getAccNumber barabar bashe ba accnumber bedehkar dar file payment
            if (calculatedPaymentTO.getDebtorAccountNumber().equals(accountTO.getAccNumber())) {
                //check mojodie acc bedehkar ba kole mablaghe hoghoghe karmandan
                if (calculatedPaymentTO.getTotalDebt() > accountTO.getAmount()) {
                    throw new Exception("mojodire hesabe "+accountTO.getAccNumber()+" kafi nist");
                }
            }
        }


    }
}
