package thoughtworks.com.json;

import thoughtworks.com.domain.Price;

public class PriceJson {
    private final Price price;

    public PriceJson(Price price) {

        this.price = price;
    }

    public double getAmount() {
        return price.getAmount();
    }

    public String getEffectDate() {
        return String.valueOf(price.getEffectDate());
    }
}
