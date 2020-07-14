package ir.dotin.service;

import ir.dotin.dto.AccountDTO;
import ir.dotin.dto.CalculatedPaymentDTO;
import ir.dotin.thread.PaymentThread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SalaryPaymentService {
    public void handleSalaryPayment(Integer numberThread) throws Exception {

        // Business Implementation
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

        // Multi-Thread with ExecutorService and FixedThreadPool method
        Integer size = paymentList.size();
        if (numberThread > size) {
            numberThread = size;
        }
        Integer kharejeGhesmat = size / numberThread;
        Integer baghimande = size % numberThread;

        ExecutorService es = Executors.newFixedThreadPool(numberThread);
        CountDownLatch countDownLatch = new CountDownLatch(numberThread);
        for (int i = 0; i < numberThread; i++) {
            Integer index = i * kharejeGhesmat;
            List<AccountDTO> subAccList;

            // Agar threade akhar bood va baghimande ham dashtim:
            if (i + 1 == numberThread && baghimande > 0) {
                subAccList = paymentList.subList(index, kharejeGhesmat + index + baghimande);
            } else {
                subAccList = paymentList.subList(index, kharejeGhesmat + index);
            }
            Runnable runnable = new PaymentThread(subAccList, balanceMap, calculatedPaymentDTO, countDownLatch);
            es.execute(runnable);
        }
        countDownLatch.await();
        es.shutdown();

        // Updating Balance File
        balanceService.updateBalanceFile(balanceMap);
    }
}