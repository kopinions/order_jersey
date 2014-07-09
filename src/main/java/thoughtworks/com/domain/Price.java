package thoughtworks.com.domain;

import org.bson.types.ObjectId;

import java.util.Date;

public class Price {
    private double amount;
    private Date effectDate;
    private ObjectId id;

    public Price(double amount, Date effectDate) {
        this.amount = amount;
        this.effectDate = effectDate;
    }

    public Price() {
    }

    public Price(ObjectId id, double amount, Date effectDate) {
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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
