package store.view;

import store.Order;
import store.Product;
import store.Promotion;

import java.util.List;

public class OutputView {
    public static void printProducts(List<Product> products) {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        for (Product product : products) {
            Promotion promotion = product.getPromotion();
            String price = String.format("%,d", product.getPrice()) + "원";
            String quantity = product.getQuantity() + "개";
            if (product.getQuantity() == 0) {
                quantity = "재고 없음";
            }
            System.out.printf("- %s %s %s ", product.getName(), price, quantity);
            try {
                System.out.println(promotion.getName());
            } catch (NullPointerException e) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void printReceipt(List<Order> orders) {
        System.out.println("===========W 편의점=============\n상품명\t\t수량\t\t금액");
        for (Order order : orders) {
            System.out.println(order.getNonePromotionProduct().getName() + "\t\t\t" + order.getQuantity() + "\t" + String.format("%,d", order.getNonePromotionProduct().getPrice()));
        }
        System.out.println("===========증\t정=============");
        for (Order order : orders) {
            if (order.countPromotionQuantity() > 0) {
                System.out.println(order.getNonePromotionProduct().getName() + "\t\t\t" + order.countFreeItem());
            }
        }
        System.out.println("==============================");
        System.out.println("총구매액\t\t\t" + orders.stream().mapToInt(Order::getQuantity).sum() + "\t\t" + String.format("%,d", orders.stream().mapToInt(Order::totalUseMoney).sum()));
        System.out.println("행사할인\t\t\t\t\t-" + String.format("%,d", orders.stream().mapToInt(Order::promotionDiscount).sum()));
        System.out.println("멤버십할인\t\t\t\t\t-" + String.format("%,d", orders.stream().mapToInt(Order::membershipDiscount).sum()));
        System.out.println("내실돈\t\t\t\t\t" + String.format("%,d", orders.stream().mapToInt(Order::calculatePrice).sum()));
    }
}
