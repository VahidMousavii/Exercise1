package ir.dotin;

import ir.dotin.service.*;


public class Main {
    public static void main(String[] args) throws Exception {

        SalaryPaymentService salaryPaymentService = new SalaryPaymentService();
        salaryPaymentService.handleSalaryPayment(10);
    }
}
