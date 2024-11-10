package store.promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {
    private static final PromotionRepository instance = new PromotionRepository();
    private final List<Promotion> promotions = new ArrayList<>();

    public static PromotionRepository getInstance() {
        return instance;
    }

    public void add(Promotion promotion) {
        promotions.add(promotion);
    }

    public Promotion findByName(String promotionName) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }
}
