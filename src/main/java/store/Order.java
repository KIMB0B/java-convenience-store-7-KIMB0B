package store;

import java.util.List;
import java.util.Map;

public class Order {
    private final List<Promotion> promotions;
    private final Map<String, Integer> buyingItems;
    private final boolean isMembership;

    public Order(List<Promotion> promotions, Map<String, Integer> buyingItems, boolean isMembership) {
        this.promotions = promotions;
        this.buyingItems = buyingItems;
        this.isMembership = isMembership;
    }
}
