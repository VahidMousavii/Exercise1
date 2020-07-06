package ir.dotin.service;

import com.sun.deploy.util.SyncAccess;
import ir.dotin.dto.TransactionDTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class TransactionService {
    //write into transaction.txt in append mode.
    public static final Long lock = 0L;

    public void appendTransaction(List<TransactionDTO> transactionDTOS) {
        synchronized (lock) {
            try {
                Files.write(Paths.get("transaction.txt"), transactionDTOS, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    }
}
