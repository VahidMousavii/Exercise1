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

    public void updateBalanceFile(List<AccountTO> balanceList) {
        List<String> strings = new ArrayList<>();
        for (AccountTO account : balanceList) {
            strings.add(account.getAccNumber() + " " + account.getAmount());
        }
        try {
            Files.write(Paths.get(StaticString.balancePath), strings, StandardCharsets.UTF_8);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public List<AccountTO> balanceRead(String path) {
        List<AccountTO> balanceList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                AccountTO account = new AccountTO(values[0], Long.parseLong(values[1]));
                balanceList.add(account);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return balanceList;
    }

    public CalculatedPaymentTO calculateTotalCredit(List<AccountTO> paymentList) {
        CalculatedPaymentTO calculatedPaymentTO = new CalculatedPaymentTO();
        Long totalCredit = 0L;
        Long totalDebt = 0L;
        for (AccountTO accountTO : paymentList) {
            if (accountTO.getType().equals("debtor")) {
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

    public List<AccountTO> payment(List<AccountTO> paymentList, List<AccountTO> balanceList, CalculatedPaymentTO calculatedPaymentTO) {
        AccountTO debtorAcc = null;
        List<String> strings = new ArrayList<>();
        for (AccountTO accountTO : balanceList) {
            if (accountTO.getAccNumber().equals(calculatedPaymentTO.getDebtorAccountNumber())) {
                debtorAcc = accountTO;
            }
        }

        for (AccountTO accountTO : paymentList) {
            for (AccountTO balanceTO : balanceList) {
                if (balanceTO.getAccNumber().equals(accountTO.getAccNumber()) && accountTO.getType().equals(StaticString.creditor)) {
                    balanceTO.setAmount(balanceTO.getAmount() + accountTO.getAmount());
                    debtorAcc.setAmount(debtorAcc.getAmount() - accountTO.getAmount());
                    strings.add(debtorAcc.getAccNumber() + " " + accountTO.getAccNumber() + " " + accountTO.getAmount());
                }
            }
        }
        appendTransaction(strings);
        return balanceList;
    }

    public void appendTransaction(List<String> strings) {

        try {
            Files.write(Paths.get(StaticString.transactionPath), strings, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}