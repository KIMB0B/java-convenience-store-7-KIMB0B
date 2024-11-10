package store.view;

import store.order.Order;
import store.order.OrderService;
import store.product.Product;
import store.product.ProductService;

import java.util.List;

public class OutputView {
    private static final ProductService productService = ProductService.getInstance();
    private static final OrderService orderService = OrderService.getInstance();

    public static void printProducts() {
        List<Product> products = productService.findALl();
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        for (Product product : products) {
            printProduct(product);
        }
        System.out.println();
    }

    private static void printProduct(Product product) {
        String quantity = product.getQuantity() + "개";
        if (product.getQuantity() == 0) {
            quantity = "재고 없음";
        }
        System.out.printf("- %s %s %s ", product.getName(), String.format("%,d", product.getPrice()) + "원", quantity);
        try {
            System.out.println(product.getPromotion().getName());
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    public static void printReceipt() {
        printReceiptHeader();
        printFreeGift();
        printReceiptSummary();
    }

    private static void printReceiptHeader() {
        System.out.println("===========W 편의점=============\n상품명\t\t수량\t\t금액");
        for (Order order : orderService.findAll()) {
            System.out.println(order.getNonePromotionProduct().getName() + "\t\t\t" + order.getQuantity() + "\t" + String.format("%,d", order.totalUseMoney()));
        }
    }

    private static void printFreeGift() {
        System.out.println("============증정==============");
        for (Order order : orderService.findAll()) {
            if (order.countPromotionQuantity() > 0) {
                System.out.println(order.getNonePromotionProduct().getName() + "\t\t\t" + order.countFreeItem());
            }
        }
    }

    private static void printReceiptSummary() {
        List<Order> orders = orderService.findAll();

        System.out.println("==============================");
        System.out.println("총구매액\t\t\t" + orders.stream().mapToInt(Order::getQuantity).sum() + "\t\t" + String.format("%,d", orders.stream().mapToInt(Order::totalUseMoney).sum()));
        System.out.println("행사할인\t\t\t\t\t-" + String.format("%,d", orders.stream().mapToInt(Order::promotionDiscount).sum()));
        System.out.println("멤버십할인\t\t\t\t\t-" + String.format("%,d", orders.stream().mapToInt(Order::membershipDiscount).sum()));
        System.out.println("내실돈\t\t\t\t\t" + String.format("%,d", orders.stream().mapToInt(Order::calculatePrice).sum()));
        System.out.println();
    }
}
