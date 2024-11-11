package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.promotion.Promotion;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionEntityTest {

    private Promotion promotion;

    @BeforeEach
    void setUp() {
        String name = "MD추천상품";
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 31, 23, 59);
        promotion = new Promotion(name,1,1,startDate,endDate);
    }

    @Test
    void 프로모션_기간인지_확인() {
        LocalDateTime nowDate = LocalDateTime.of(2024, 1, 15, 0, 0);
        assertThat(promotion.isPromotion(nowDate)).isTrue();
    }

    @Test
    void 프로모션_기간이_지났는지_확인() {
        LocalDateTime nowDate = LocalDateTime.of(2024, 2, 15, 0, 0);
        assertThat(promotion.isPromotion(nowDate)).isFalse();
    }

}
