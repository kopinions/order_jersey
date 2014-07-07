package thoughtworks.com.json;

import thoughtworks.com.domain.Price;

public class PriceRefJson {
    private final Price price;

    public PriceRefJson(Price price) {

        this.price = price;
    }

    public double getAmount() {
        return price.getAmount();
    }

    public String getEffectDate() {
        return price.getEffectDate().toString();
    }
}
