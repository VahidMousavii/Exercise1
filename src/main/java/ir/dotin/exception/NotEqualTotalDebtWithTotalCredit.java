package ir.dotin.exception;

public class NotEqualTotalDebtWithTotalCredit extends Exception {
    Long mablagheBedehi;
    Long hughugheKarmandan;

    public NotEqualTotalDebtWithTotalCredit(Long mablagheBedehi, Long hughugheKarmandan) {
        this.mablagheBedehi = mablagheBedehi;
        this.hughugheKarmandan= hughugheKarmandan;
    }

    public String toString() {
        return " dar ejraye barname khatayi rokh dade: mablaghe bedehi ba mablaghe bestankar barabar nist. meghdare mablaghe bedehie sherkat = " + mablagheBedehi+ ". mablaghe kole hughughe karmandan = " + hughugheKarmandan;
    }
}