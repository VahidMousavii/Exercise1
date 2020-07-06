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
        List<String> pList = Arrays.asList("debtor 1.10.100.1 6000",
                "creditor 1.20.100.1 300",
                "creditor 1.20.100.2 300",
                "creditor 1.20.100.3 300",
                "creditor 1.20.100.4 300",
                "creditor 1.20.100.5 300",
                "creditor 1.20.100.6 300",
                "creditor 1.20.100.7 300",
                "creditor 1.20.100.8 300",
                "creditor 1.20.100.9 300",
                "creditor 1.20.100.10 300",
                "creditor 1.20.100.11 300",
                "creditor 1.20.100.12 300",
                "creditor 1.20.100.13 300",
                "creditor 1.20.100.14 300",
                "creditor 1.20.100.15 300",
                "creditor 1.20.100.16 300",
                "creditor 1.20.100.17 300",
                "creditor 1.20.100.18 300",
                "creditor 1.20.100.19 300",
                "creditor 1.20.100.20 300");

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
}
