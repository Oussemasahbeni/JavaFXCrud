package classes;

import java.sql.Date;

public class Facture {
    private String numFacture;
    private Date dateFacture;
    private float total;
    private String refCli;

    public String getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getRefCli() {
        return refCli;
    }

    public void setRefCli(String refCli) {
        this.refCli = refCli;
    }

    public Facture(String numFacture, Date dateFacture, float total, String refCli) {
        this.numFacture = numFacture;
        this.dateFacture = dateFacture;
        this.total = total;
        this.refCli = refCli;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "numFacture='" + numFacture + '\'' +
                ", dateFacture=" + dateFacture +
                ", total=" + total +
                ", refCli='" + refCli + '\'' +
                '}';
    }
}
