package thoughtworks.com.domain;

import java.util.Date;

public class Price {
    private double amount;
    private Date effectDate;
    private int id;

    public Price(double amount, Date effectDate) {
        this.amount = amount;
        this.effectDate = effectDate;
    }

    public Price() {
    }

    public Price(int id, double amount, Date effectDate) {
        this.id = id;
        this.amount = amount;
        this.effectDate = effectDate;
    }

    public double getAmount() {
        return amount;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public int getId() {
        return id;
    }
}
