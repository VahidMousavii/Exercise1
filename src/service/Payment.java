package service;

import main.to.AccountTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}