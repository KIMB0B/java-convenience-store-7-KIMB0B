package store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.product.Product;
import store.promotion.Promotion;

import java.time.LocalDateTime;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductEntityTest {

    private Promotion promotion;
    private Product promotionalProduct;
    private Product nonPromotionalProduct;

    @BeforeEach
    void setUp() {
        String promotionName = "MD추천상품";
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 31, 23, 59);
        promotion = new Promotion(promotionName,1,1,startDate,endDate);

        promotionalProduct = new Product("오렌지주스", 1800, 9, promotion);
        nonPromotionalProduct = new Product("사과주스", 1500, 10, null);
    }

    @Test
    void 상품_정보_재지정_테스트() {
        promotionalProduct.setProduct("사과주스", 1500, 10, null);
        assertThat(promotionalProduct.getName()).isEqualTo(nonPromotionalProduct.getName());
    }

    @Test
    void 물건_개수를_넘는_수량_구매_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> promotionalProduct.setQuantity(-2))
                        .isInstanceOf(IllegalArgumentException.class)
        );

    }
}
