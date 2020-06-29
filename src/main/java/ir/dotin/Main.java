package ir.dotin;


import ir.dotin.dto.AccountDTO;
import ir.dotin.dto.CalculatedPaymentDTO;
import ir.dotin.service.AccountValidate;
import ir.dotin.service.BalanceService;
import ir.dotin.service.PaymentService;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

   /*

        List<AccountDTO> paymentList = payment.paymentRead(StaticString.paymentPath);

        Map<String,AccountDTO> balanceMap = payment.balanceRead(StaticString.balancePath);

        CalculatedPaymentDTO calculatedPaymentDTO = payment.calculateTotalCredit(paymentList);

        AccountValidate accountValidate = new AccountValidate();
        accountValidate.checkTotalDebtWithTotalCredit(calculatedPaymentDTO);
        accountValidate.checkDebtorBalance(calculatedPaymentDTO, balanceMap);
        payment.payment(paymentList,balanceMap,calculatedPaymentDTO);
        payment.updateBalanceFile(balanceMap);
    }*/


        PaymentService paymentService = new PaymentService();
        paymentService.paymentWriter();
        BalanceService balanceService = new BalanceService();
        balanceService.balanceWriter();
        List<AccountDTO> paymentList = paymentService.paymentRead("payment.txt");
        Map<String, AccountDTO> balanceMap = balanceService.balanceRead("balance.txt");
        CalculatedPaymentDTO calculatedPaymentDTO = paymentService.calculateTotalCredit(paymentList);
        AccountValidate accountValidate = new AccountValidate();
        accountValidate.checkTotalDebtWithTotalCredit(calculatedPaymentDTO);
        accountValidate.checkDebtorBalance(calculatedPaymentDTO, balanceMap);
        accountValidate.checkCreditorAccInBalanceFile(paymentList, balanceMap);
        paymentService.payment(paymentList, balanceMap, calculatedPaymentDTO);
        balanceService.updateBalanceFile(balanceMap);
    }
}
