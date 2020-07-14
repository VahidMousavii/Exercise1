package ir.dotin.thread;

import ir.dotin.dto.AccountDTO;
import ir.dotin.dto.CalculatedPaymentDTO;
import ir.dotin.dto.TransactionDTO;
import ir.dotin.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class PaymentThread implements Runnable {
    List<AccountDTO> paymentList;
    Map<String, AccountDTO> balanceMap;
    CalculatedPaymentDTO calculatedPaymentDTO;
    CountDownLatch countDownLatch;

    public PaymentThread(List<AccountDTO> paymentList, Map<String, AccountDTO> balanceMap, CalculatedPaymentDTO calculatedPaymentDTO, CountDownLatch countDownLatch) {
        this.paymentList = paymentList;
        this.balanceMap = balanceMap;
        this.calculatedPaymentDTO = calculatedPaymentDTO;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        List<TransactionDTO> transactionList = new ArrayList<>();
        for (AccountDTO paymentTO : paymentList) {
            if (paymentTO.getType().equals("creditor")) {
                AccountDTO debtorAcc;
                // Using synch for making thread-safe
                synchronized (PaymentThread.class) {
                    AccountDTO creditorBalanceTO = balanceMap.get(paymentTO.getAccNumber());
                    // Ezafe kardan be mojudie hesabe karmand
                    creditorBalanceTO.setAmount(paymentTO.getAmount() + creditorBalanceTO.getAmount());
                    debtorAcc = balanceMap.get(calculatedPaymentDTO.getDebtorAccountNumber());
                    // Kasr az mojudie hesabe sherkat
                    debtorAcc.setAmount(debtorAcc.getAmount() - paymentTO.getAmount());
                }
                // Ezafe kardane record ha be liste transaction
                transactionList.add(new TransactionDTO(debtorAcc.getAccNumber(), paymentTO.getAccNumber(), paymentTO.getAmount()));
            }
        }
        // Calling appendTransaction() for writing into transaction.txt
        TransactionService transactionService = new TransactionService();
        transactionService.appendTransaction(transactionList);

        // Counting CountDownLatch after appending to transaction.txt
        countDownLatch.countDown();

    }
}
