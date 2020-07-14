package ir.dotin.exception;

public class NotEnoughBalanceException extends Exception {
    String shomareHesab;
    Long mablagheMojodi;
    Long mablagheBedehi;

    public NotEnoughBalanceException(String shomareHesab, Long mablagheMojodi, Long mablagheBedehi) {
        this.shomareHesab = shomareHesab;
        this.mablagheMojodi = mablagheMojodi;
        this.mablagheBedehi = mablagheBedehi;
    }

    public String toString() {
        return "dar ejraye barname khatayi rokh dade: meghdare mojudie hesab be shomareye: " + shomareHesab
                + "  kafi nemibashad." +
                " mableghe mojodi= " + mablagheMojodi + ". " +
                "mablaghe bedehi= " + mablagheBedehi;
    }
}