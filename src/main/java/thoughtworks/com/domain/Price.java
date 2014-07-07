package thoughtworks.com.domain;

import java.util.Date;

public class Price {
    private double amount;
    private Date effectDate;

    public Price(double amount, Date effectDate) {
        this.amount = amount;
        this.effectDate = effectDate;
    }

    public Price() {
    }

    public double getAmount() {
        return amount;
    }

    public Date getEffectDate() {
        return effectDate;
    }
}
