package main;

import main.constant.StaticString;
import main.to.AccountTO;
import main.to.CalculatedPaymentTO;
import main.validation.AccountValidate;
import service.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Payment payment = new Payment();

        List<AccountTO> paymentList = payment.paymentRead(StaticString.paymentPath);

        Map<String,AccountTO> balanceMap = payment.balanceRead(StaticString.balancePath);

        CalculatedPaymentTO calculatedPaymentTO = payment.calculateTotalCredit(paymentList);

        AccountValidate accountValidate = new AccountValidate();
        accountValidate.checkTotalDebtWithTotalCredit(calculatedPaymentTO);
        accountValidate.checkDebtorBalance(calculatedPaymentTO, balanceMap);
        payment.payment(paymentList,balanceMap,calculatedPaymentTO);
        payment.updateBalanceFile(balanceMap);
    }
}
