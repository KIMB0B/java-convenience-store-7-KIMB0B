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

    public static void main(String[] args) {
        promotionService.loadByMarkdown(promotionFilePath);
        productService.loadByMarkdown(productFilePath);
        boolean oneMore = true;

        while (oneMore) {
            OutputView.printProducts();
            Map<String, Integer> buyingItems = InputView.readItem();

            try {
                orderService.addOrdersByItemMap(buyingItems);
                productService.updateProductsQuantity();
                OutputView.printReceipt();
                orderService.clear();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                oneMore = false;
                continue;
            }

            if (oneMore) {
                oneMore = InputView.readOneMore().equalsIgnoreCase("Y");
            }
        }
    }
}
