package ir.dotin.exception;

public class NoSameAccountInBalanceException extends Exception {
    String exceptionContent;

    public NoSameAccountInBalanceException(String exceptionContent) {
        this.exceptionContent = exceptionContent;
    }

    public String toString() {
        return " dar ejraye barname khatayi rokh dade: hesab be shomareye: " + exceptionContent + " dar file balance mojud nemibashad.";
    }
}