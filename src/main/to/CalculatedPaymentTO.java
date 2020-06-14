package main.to;

public class CalculatedPaymentTO {
    private String totalCredit;
    private String totalDebt;
    private AccountTO debtorAccount;

    public CalculatedPaymentTO() {
    }

    public CalculatedPaymentTO(String totalCredit, String totalDebt, AccountTO debtorAccount) {
        this.totalCredit = totalCredit;
        this.totalDebt = totalDebt;
        this.debtorAccount = debtorAccount;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(String totalDebt) {
        this.totalDebt = totalDebt;
    }

    public AccountTO getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountTO debtorAccount) {
        this.debtorAccount = debtorAccount;
    }
}
