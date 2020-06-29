package ir.dotin.dto;


public class CalculatedPaymentDTO {
    private Long totalCredit;              // majmoe kole hughughe karmandan
    private Long totalDebt;                // bedehie kole sherkat be karmandan
    private String debtorAccountNumber;    // shomare hesabe sherkat ke bayad az aan bardasht beshavad

    public CalculatedPaymentDTO() {}

    public CalculatedPaymentDTO(Long totalCredit, Long totalDebt, String debtorAccountNumber) {
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
