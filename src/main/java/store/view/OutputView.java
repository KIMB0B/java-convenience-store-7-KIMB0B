package store.view;

import store.Product;
import store.Promotion;

import java.util.List;

public class OutputView {
    public static void printProducts(List<Product> products) {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        for (Product product : products) {
            Promotion promotion = product.getPromotion();
            System.out.printf("- %s %d %d ", product.getName(), product.getPrice(), product.getQuantity());
            try {
                System.out.println(promotion.getName());
            } catch (NullPointerException e) {
                System.out.println("null");
            }
        }
        System.out.println();
    }
}
