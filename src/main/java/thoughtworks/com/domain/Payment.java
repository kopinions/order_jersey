package thoughtworks.com.domain;

public class Payment {
    private String type;
    private double amount;

    public Payment(String payType, double amount) {
        type = payType;
        this.amount = amount;
    }


    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
