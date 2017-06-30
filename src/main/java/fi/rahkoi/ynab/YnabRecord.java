package fi.rahkoi.ynab;

import java.util.Date;

/**
 * Created by terorahko on 30/06/2017.
 */
public class YnabRecord {

    private Date date;
    private String payee;
    private String category;
    private String memo;
    private Double outflow;
    private Double inflow;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getOutflow() {
        return outflow;
    }

    public void setOutflow(Double outflow) {
        this.outflow = outflow;
    }

    public Double getInflow() {
        return inflow;
    }

    public void setInflow(Double inflow) {
        this.inflow = inflow;
    }

    @Override
    public String toString() {
        return "YnabRecord{" +
                "date=" + date +
                ", payee='" + payee + '\'' +
                ", category='" + category + '\'' +
                ", memo='" + memo + '\'' +
                ", outflow=" + outflow +
                ", inflow=" + inflow +
                '}';
    }
}
