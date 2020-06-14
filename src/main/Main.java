package main;

import main.constant.StaticString;
import main.to.AccountTO;
import service.Payment;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Payment payment = new Payment();
        List<AccountTO> amounts = payment.paymentRead(StaticString.paymentPath);
        for (AccountTO amount : amounts) {
            System.out.println(amount);
        }
        System.out.println("********************");

        List<AccountTO> types = payment.balanceRead(StaticString.balancePath);
        for (AccountTO type : types) {
            System.out.println(type);
        }
    }
}
