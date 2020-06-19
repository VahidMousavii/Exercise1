package service;

import main.constant.StaticString;
import main.to.AccountTO;
import main.to.CalculatedPaymentTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Payment {

    public List<AccountTO> paymentRead(String path) {
        List<AccountTO> accountList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                AccountTO account = new AccountTO(values[0], values[1], Long.parseLong(values[2]));

                accountList.add(account);

            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return accountList;
    }

    public Map<String, AccountTO> balanceRead(String path) {
        Map<String, AccountTO> balanceMap = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                AccountTO account = new AccountTO(values[0], Long.parseLong(values[1]));
                balanceMap.put(account.getAccNumber(), account);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return balanceMap;
    }

    public CalculatedPaymentTO calculateTotalCredit(List<AccountTO> paymentList) {
        CalculatedPaymentTO calculatedPaymentTO = new CalculatedPaymentTO();
        Long totalCredit = 0L;
        Long totalDebt = 0L;
        for (AccountTO accountTO : paymentList) {
            if (accountTO.getType().equals(StaticString.debtor)) {
                totalDebt = accountTO.getAmount();
                calculatedPaymentTO.setDebtorAccountNumber(accountTO.getAccNumber());
            } else {
                totalCredit = totalCredit + accountTO.getAmount();
            }
        }
        calculatedPaymentTO.setTotalCredit(totalCredit);
        calculatedPaymentTO.setTotalDebt(totalDebt);
        return calculatedPaymentTO;
    }

    public void payment(List<AccountTO> paymentList, Map<String, AccountTO> balanceMap, CalculatedPaymentTO calculatedPaymentTO) throws Exception {
        List<String> transactionList = new ArrayList<>();

        for (AccountTO paymentTO : paymentList) {
            if (paymentTO.getType().equals(StaticString.creditor)) {
                AccountTO creditorBalanceTO = balanceMap.get(paymentTO.getAccNumber());
                creditorBalanceTO.setAmount(paymentTO.getAmount() + creditorBalanceTO.getAmount());
                AccountTO debtorAcc = balanceMap.get(calculatedPaymentTO.getDebtorAccountNumber());
                debtorAcc.setAmount(debtorAcc.getAmount() - paymentTO.getAmount());
                transactionList.add(debtorAcc.getAccNumber() + " " + paymentTO.getAccNumber() + " " + paymentTO.getAmount());

            }

        }
        appendTransaction(transactionList);
    }

    public void updateBalanceFile(Map<String, AccountTO> balanceMap) {
        List<String> strings = new ArrayList<>();
        for (AccountTO balanceTO : balanceMap.values()) {
            strings.add(balanceTO.getAccNumber() + " " + balanceTO.getAmount());
            try {
                Files.write(Paths.get(StaticString.balancePath), strings, StandardCharsets.UTF_8);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    }

    public void appendTransaction(List<String> strings) {

        try {
            Files.write(Paths.get(StaticString.transactionPath), strings, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}