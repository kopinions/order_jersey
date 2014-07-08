package thoughtworks.com.domain;

public class Payment {
    private String type;

    public Payment(String payType) {
        type = payType;
    }

    public String getType() {
        return type;
    }
}
