package ir.dotin.dto;


public class TransactionDTO implements CharSequence {
    private String debtorACC;
    private String creditorACC;
    private Long amount;

    public TransactionDTO(String debtorACC, String creditorACC, Long amount) {
        this.debtorACC = debtorACC;
        this.creditorACC = creditorACC;
        this.amount = amount;
    }

    public TransactionDTO() {
    }

    public String getDebtorACC() {
        return debtorACC;
    }

    public void setDebtorACC(String debtorACC) {
        this.debtorACC = debtorACC;
    }

    public String getCreditorACC() {
        return creditorACC;
    }

    public void setCreditorACC(String creditorACC) {
        this.creditorACC = creditorACC;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }


    @Override
    public int length() {
        return this.toString().length();
    }

    @Override
    public char charAt(int index) {
        return this.toString().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.toString().subSequence(start, end);
    }

    @Override
    public String toString() {
        return debtorACC + " " + creditorACC + " " + amount;
    }
}
