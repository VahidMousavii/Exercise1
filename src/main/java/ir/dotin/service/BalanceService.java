package ir.dotin.service;

import ir.dotin.dto.AccountDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BalanceService {
    // Tolide file balance.txt
    public void balanceWriter() {
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> bList = Arrays.asList("1.10.100.1 6000",
                "1.20.100.1 0",
                "1.20.100.2 0",
                "1.20.100.3 0",
                "1.20.100.4 0",
                "1.20.100.5 0",
                "1.20.100.6 0",
                "1.20.100.7 0",
                "1.20.100.8 0",
                "1.20.100.9 0",
                "1.20.100.10 0",
                "1.20.100.11 0",
                "1.20.100.12 0",
                "1.20.100.13 0",
                "1.20.100.14 0",
                "1.20.100.15 0",
                "1.20.100.16 0",
                "1.20.100.17 0",
                "1.20.100.18 0",
                "1.20.100.19 0",
                "1.20.100.20 0");

        try {
            Files.write(Paths.get("balance.txt"), bList, utf8);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    // Khoondane file balance va return 1Map
    public Map<String, AccountDTO> balanceRead(String path) {

        Map<String, AccountDTO> balanceMap = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            // Read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                AccountDTO account = new AccountDTO(values[0], Long.parseLong(values[1]));
                balanceMap.put(account.getAccNumber(), account);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return balanceMap;
    }

    // Update kardane file balance.txt
    public void updateBalanceFile(Map<String, AccountDTO> balanceMap) {
        List<String> strings = new ArrayList<>();
        for (AccountDTO balanceTO : balanceMap.values()) {
            strings.add(balanceTO.getAccNumber() + " " + balanceTO.getAmount());
            try {
                Files.write(Paths.get("balance.txt"), strings, StandardCharsets.UTF_8);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    }
}
