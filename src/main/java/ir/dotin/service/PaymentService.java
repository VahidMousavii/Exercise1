package ir.dotin.service;

import ir.dotin.dto.AccountDTO;
import ir.dotin.dto.CalculatedPaymentDTO;
import ir.dotin.dto.TransactionDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PaymentService {
    // tolide file payment.txt
    public void paymentWriter() {
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> pList = Arrays.asList("debtor 1.10.100.1 1000", "creditor 1.20.100.1 300", "creditor 1.20.100.2 700");

        try {
            // If the file doesn't exists, create and write to it
            // If the file exists, truncate (remove all content) and write to it
            Files.write(Paths.get("payment.txt"), pList, utf8);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    // khandane file payment.txt
    public List<AccountDTO> paymentRead(String path) {
        List<AccountDTO> accountList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                AccountDTO account = new AccountDTO(values[0], values[1], Long.parseLong(values[2]));

                accountList.add(account);

            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return accountList;
    }

    //mohasebeye majmooE bedehie sherkat va meghdare kolle hughughe karmandan
    public CalculatedPaymentDTO calculateTotalCredit(List<AccountDTO> paymentList) {
        CalculatedPaymentDTO calculatedPaymentDTO = new CalculatedPaymentDTO();
        Long totalCredit = 0L;
        Long totalDebt = 0L;
        for (AccountDTO accountDTO : paymentList) {
            if (accountDTO.getType().equals("debtor")) {
                totalDebt = accountDTO.getAmount();
                calculatedPaymentDTO.setDebtorAccountNumber(accountDTO.getAccNumber());
            } else {
                totalCredit = totalCredit + accountDTO.getAmount();
            }
        }
        calculatedPaymentDTO.setTotalCredit(totalCredit);
        calculatedPaymentDTO.setTotalDebt(totalDebt);
        return calculatedPaymentDTO;
    }

    // anjame amaliate kasre pul az hesabe sherkat va afzayesh be hesabe karmand
    public void payment(List<AccountDTO> paymentList, Map<String, AccountDTO> balanceMap, CalculatedPaymentDTO calculatedPaymentDTO) throws Exception {
        List<TransactionDTO> transactionList = new ArrayList<>();

        for (AccountDTO paymentTO : paymentList) {
            if (paymentTO.getType().equals("creditor")) {
                AccountDTO creditorBalanceTO = balanceMap.get(paymentTO.getAccNumber());
                //ezafe kardan be mojudie hesabe karmand
                creditorBalanceTO.setAmount(paymentTO.getAmount() + creditorBalanceTO.getAmount());
                AccountDTO debtorAcc = balanceMap.get(calculatedPaymentDTO.getDebtorAccountNumber());
                //kam kardan az mojudie hesabe sherkat
                debtorAcc.setAmount(debtorAcc.getAmount() - paymentTO.getAmount());
                //ezafe kardane record ha be liste transaction
                transactionList.add(new TransactionDTO(debtorAcc.getAccNumber(), paymentTO.getAccNumber(), paymentTO.getAmount()));
            }
        }
        //calling appendTransaction() for writing into transaction.txt
        TransactionService transactionService = new TransactionService();
        transactionService.appendTransaction(transactionList);
    }

}
