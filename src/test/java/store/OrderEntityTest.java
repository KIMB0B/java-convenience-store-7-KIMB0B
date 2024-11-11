package store;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.order.Order;
import store.product.Product;
import store.promotion.Promotion;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderEntityTest {

    private Promotion promotion;
    private Product promotionalProduct;
    private Product nonPromotionalProduct;

    @BeforeEach
    void setUp() {
        String promotionName = "MD추천상품";
        // startDate는 DateTime.now()에서 한달 전으로 설정
        LocalDateTime startDate = DateTimes.now().minusMonths(1);
        LocalDateTime endDate = DateTimes.now().plusMonths(1);
        promotion = new Promotion(promotionName,2,1,startDate,endDate);

        promotionalProduct = new Product("오렌지주스", 1500, 9, promotion);
        nonPromotionalProduct = new Product("오렌지주스", 1500, 10, null);
    }

    @Test
    void 프로모션이_없는_상품을_주문했을_때_프로모션_적용_가능_물건_개수_확인() {
        Order order = new Order(nonPromotionalProduct, 5);
        int promotionalQuantity = order.promotionalQuantityCount();
        assertThat(promotionalQuantity).isEqualTo(0);
    }

    @Test
    void 프로모션이_있는_상품을_프로모션_적용_물건보다_더_많이_주문했을_떄() {
        Order order = new Order(promotionalProduct, nonPromotionalProduct, 10);
        int nonePromotionalQuantity = order.nonePromotionalQuantityCount();
        assertThat(nonePromotionalQuantity).isEqualTo(1);
    }

    @Test
    void 프로모션_무료_제공_물건_개수_확인() {
        Order order = new Order(promotionalProduct, nonPromotionalProduct, 10);
        int freeItems = order.freeItemCount();
        assertThat(freeItems).isEqualTo(3);
    }

    @Test
    void 프로모션_적용_가능한_물건_개수() {
        Order order = new Order(promotionalProduct, nonPromotionalProduct, 8);
        int additionalItems = order.additionalItemCount();
        assertThat(additionalItems).isEqualTo(1);

        order = new Order(promotionalProduct, nonPromotionalProduct, 9);
        additionalItems = order.additionalItemCount();
        assertThat(additionalItems).isEqualTo(0);
    }

    @Test
    void 프로모션_할인_가격_확인() {
        Order order = new Order(promotionalProduct, nonPromotionalProduct, 5);
        int discountPrice = order.promotionDiscountPrice();
        assertThat(discountPrice).isEqualTo(1500);
    }

    @Test
    void 멤버십_할인_가격_확인() {
        Order order = new Order(promotionalProduct, nonPromotionalProduct, 5);
        order.setMembership();
        int membershipDiscount = order.membershipDiscountPrice();
        assertThat(membershipDiscount).isEqualTo(900);
    }

    @Test
    void 총_결제_금액_확인() {
        Order order = new Order(promotionalProduct, nonPromotionalProduct, 5);
        int totalPrice = order.totalUsePrice();
        assertThat(totalPrice).isEqualTo(7500);
    }

    @Test
    void 할인_적용_후_최종_결제_금액_확인() {
        Order order = new Order(promotionalProduct, nonPromotionalProduct, 5);
        order.setMembership();
        int finalPrice = order.finalCalculatePrice();
        assertThat(finalPrice).isEqualTo(5100); // 7500 - 1500 - 900
    }
}
