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
    //ijaade file balance.txt
    public void balanceWriter() {
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> bList = Arrays.asList("1.10.100.1 1000", "1.20.100.1 0", "1.20.100.2 0");

        try {
            // If the file doesn't exists, create and write to it
            // If the file exists, truncate (remove all content) and write to it
            Files.write(Paths.get("balance.txt"), bList, utf8);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    //khaandane file balance va return 1Map
    public Map<String, AccountDTO> balanceRead(String path) {
        Map<String, AccountDTO> balanceMap = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            // read line by line
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

    //update kardane file balance.txt
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
