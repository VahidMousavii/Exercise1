package ir.dotin;


import ir.dotin.dto.AccountDTO;
import ir.dotin.dto.CalculatedPaymentDTO;
import ir.dotin.service.*;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        SalaryPaymentService salaryPaymentService = new SalaryPaymentService();
        salaryPaymentService.handleSalaryPayment(2);
    }
}
