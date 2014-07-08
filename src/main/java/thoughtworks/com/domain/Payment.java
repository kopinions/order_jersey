package thoughtworks.com.domain;

public class Payment {
    private String payType;
    private double amount;
    private int id;

    public Payment() {
    }

    public Payment(String payType, double amount) {
        this.payType = payType;
        this.amount = amount;
    }


    public String getPayType() {
        return payType;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }
}
