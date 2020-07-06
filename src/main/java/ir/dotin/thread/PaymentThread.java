package ir.dotin.thread;

import ir.dotin.dto.AccountDTO;
import ir.dotin.dto.CalculatedPaymentDTO;
import ir.dotin.dto.TransactionDTO;
import ir.dotin.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentThread implements Runnable {
    List<AccountDTO> paymentList;
    Map<String, AccountDTO> balanceMap;
    CalculatedPaymentDTO calculatedPaymentDTO;

    public PaymentThread(List<AccountDTO> paymentList, Map<String, AccountDTO> balanceMap, CalculatedPaymentDTO calculatedPaymentDTO) {
        this.paymentList = paymentList;
        this.balanceMap = balanceMap;
        this.calculatedPaymentDTO = calculatedPaymentDTO;
    }

    @Override
    public void run() {


        List<TransactionDTO> transactionList = new ArrayList<>();

        for (AccountDTO paymentTO : paymentList) {
            if (paymentTO.getType().equals("creditor")) {
                AccountDTO debtorAcc;
                synchronized (PaymentThread.class) {
                    AccountDTO creditorBalanceTO = balanceMap.get(paymentTO.getAccNumber());
                    //ezafe kardan be mojudie hesabe karmand
                    creditorBalanceTO.setAmount(paymentTO.getAmount() + creditorBalanceTO.getAmount());
                    debtorAcc = balanceMap.get(calculatedPaymentDTO.getDebtorAccountNumber());
                    //kam kardan az mojudie hesabe sherkat
                    debtorAcc.setAmount(debtorAcc.getAmount() - paymentTO.getAmount());

                }
                //ezafe kardane record ha be liste transaction
                transactionList.add(new TransactionDTO(debtorAcc.getAccNumber(), paymentTO.getAccNumber(), paymentTO.getAmount()));
            }
        }
        //calling appendTransaction() for writing into transaction.txt
        TransactionService transactionService = new TransactionService();
        transactionService.appendTransaction(transactionList);
    }
}
