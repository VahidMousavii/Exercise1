package main;

import main.constant.StaticString;
import main.to.AccountTO;
import main.to.CalculatedPaymentTO;
import main.validation.AccountValidate;
import service.Payment;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Payment payment = new Payment();
        List<AccountTO> paymentListAccount = payment.paymentRead(StaticString.paymentPath);

        List<AccountTO> balanceListAccount = payment.balanceRead(StaticString.balancePath);

        AccountValidate accountValidate = new AccountValidate();
        CalculatedPaymentTO calculatedPaymentTO = payment.calculateTotalCredit(paymentListAccount);
        accountValidate.checkTotalDebtWithTotalCredit(calculatedPaymentTO);
        accountValidate.checkDebtorBalance(calculatedPaymentTO,balanceListAccount);
    }
}
