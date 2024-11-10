package store;

import store.order.OrderService;
import store.product.ProductService;
import store.promotion.PromotionService;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class Application {

    static final String promotionFilePath = "src/main/resources/promotions.md";
    static final String productFilePath = "src/main/resources/products.md";

    static final PromotionService promotionService = PromotionService.getInstance();
    static final ProductService productService = ProductService.getInstance();
    static final OrderService orderService = OrderService.getInstance();

    static boolean oneMore = true;

    public static void main(String[] args) {
        promotionService.loadByMarkdown(promotionFilePath);
        productService.loadByMarkdown(productFilePath);

        while (oneMore) {
            OutputView.printProducts();
            Map<String, Integer> buyingItems = InputView.readItem();

            try {
                orderService.addOrdersByItemMap(buyingItems);
                OutputView.printReceipt();
                productService.updateProductsQuantity();
                orderService.clear();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (oneMore) {
                oneMore = InputView.readOneMore().equalsIgnoreCase("Y");
            }
        }
    }
}
