package main.to;

public class CalculatedPaymentTO {
    private Long totalCredit;
    private Long totalDebt;
    private String debtorAccountNumber;

    public CalculatedPaymentTO() {}

    public CalculatedPaymentTO(Long totalCredit, Long totalDebt, String debtorAccountNumber) {
        this.totalCredit = totalCredit;
        this.totalDebt = totalDebt;
        this.debtorAccountNumber = debtorAccountNumber;
    }

    public Long getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Long totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Long getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Long totalDebt) {
        this.totalDebt = totalDebt;
    }

    public String getDebtorAccountNumber() {
        return debtorAccountNumber;
    }

    public void setDebtorAccountNumber(String debtorAccountNumber) {
        this.debtorAccountNumber = debtorAccountNumber;
    }
}
