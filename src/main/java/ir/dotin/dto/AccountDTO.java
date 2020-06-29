package ir.dotin.dto;

public class AccountDTO {
    private String type;
    private String accNumber;
    private Long amount;


    public AccountDTO() {
    }

    public AccountDTO(String type) {
        this.type = type;
    }

    public AccountDTO(String accNumber, Long amount) {
        this.accNumber = accNumber;
        this.amount = amount;
    }

    public AccountDTO(String type, String accNumber, Long amount) {
        this.type = type;
        this.accNumber = accNumber;
        this.amount = amount;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    @Override
    public String toString() {
        return
                        accNumber + amount;
    }
}
