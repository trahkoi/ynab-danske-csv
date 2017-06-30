package fi.rahkoi.ynab;

import java.util.Date;

/**
 * Created by terorahko on 30/06/2017.
 */
public class DanskeRecord {

    private Date date;
    private String payee;
    private Double amount;
    private Double balance;
    private String state;
    private String checked;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "DanskeRecord{" +
                "date=" + date +
                ", payee='" + payee + '\'' +
                ", amount=" + amount +
                ", balance=" + balance +
                ", state='" + state + '\'' +
                ", checked='" + checked + '\'' +
                '}';
    }
}
