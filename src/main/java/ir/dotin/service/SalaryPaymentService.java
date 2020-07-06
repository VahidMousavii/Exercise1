package ir.dotin.service;

import ir.dotin.dto.AccountDTO;
import ir.dotin.dto.CalculatedPaymentDTO;
import ir.dotin.thread.PaymentThread;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalaryPaymentService {
    public void handleSalaryPayment(Integer numberThread) throws Exception {

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

        ////////////////////////////////
        Integer size = paymentList.size();
        if (numberThread > size) {
            numberThread = size;
        }
        Integer kharejeGhesmat = size / numberThread;
        Integer baghimande = size % numberThread;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberThread; i++) {
            int index = i * kharejeGhesmat;
            List<AccountDTO> subAccPaymentDTOs = paymentList.subList(index, kharejeGhesmat + index);
            PaymentThread paymentThread = new PaymentThread(subAccPaymentDTOs, balanceMap, calculatedPaymentDTO);
            Thread thread = new Thread(paymentThread);
            threads.add(thread);
            thread.start();
        }
        if (baghimande > 0) {
            int index = size - baghimande;
            List<AccountDTO> subAccPaymentDTOs = paymentList.subList(index, size);
            PaymentThread paymentThread = new PaymentThread(subAccPaymentDTOs, balanceMap, calculatedPaymentDTO);
            Thread thread = new Thread(paymentThread);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }

        //////////////////////////
        balanceService.updateBalanceFile(balanceMap);
    }
}
