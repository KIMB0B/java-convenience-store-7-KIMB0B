package store;

import store.util.FileLoader;
import store.util.Loader;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {

    static final String promotionFilePath = "src/main/resources/promotions.md";
    static final String productFilePath = "src/main/resources/products.md";

    static List<Promotion> promotions = Loader.loadPromitions(FileLoader.loadFile(promotionFilePath));
    static List<Product> products = Loader.loadProducts(FileLoader.loadFile(productFilePath), promotions);

    static boolean oneMore = true;

    public static void main(String[] args) {
        while (oneMore) {
            OutputView.printProducts(products);
            Map<String, Integer> buyingItems = InputView.readItem();

            List<Order> orders = createOrders(buyingItems);

            OutputView.printReceipt(orders);

            updateProductsQuantity(orders);

            if (oneMore) {
                oneMore = InputView.readOneMore().equalsIgnoreCase("Y");
            }
        }
    }

    public static List<Order> createOrders(Map<String, Integer> buyingItems) {
        List<Order> orders = new ArrayList<>();

        for (Map.Entry<String, Integer> item : buyingItems.entrySet()) {
            List<Product> matchingProducts = products.stream().filter(p -> p.getName().equals(item.getKey())).toList();
            if (matchingProducts.isEmpty()) {
                return null;
            }
            if (matchingProducts.size() > 1 && matchingProducts.getFirst().getPromotion() == null) {
                return null;
            }
            orders.add(createOrder(matchingProducts, item.getValue()));
        }

        applyMembershipDiscount(orders);
        return orders;
    }

    public static Order createOrder(List<Product> matchingProducts, int quantity) {
        if (matchingProducts.size() == 1) {
            return createSingleOrder(matchingProducts.getFirst(), quantity);
        }
        return createPromotionOrder(matchingProducts, quantity);
    }

    private static Order createSingleOrder(Product product, int quantity) {
        Order order = new Order(product, quantity);
        product.sell(order.countNonePromotionQuantity());
        return order;
    }

    private static Order createPromotionOrder(List<Product> matchingProducts, int quantity) {
        Order order = new Order(matchingProducts.get(0), matchingProducts.get(1), quantity);
        if (order.canRecieveItem() > 0) {
            if (InputView.readBuyMore(matchingProducts.getFirst().getName(), order.canRecieveItem()).equals("N")) {
                return order;
            }
            order.addQuantity(order.canRecieveItem());
        }
        if (order.cantPromotionQuantity() > 0) {
            if (InputView.readCantPromotion(matchingProducts.getFirst().getName(), order.cantPromotionQuantity()).equals("Y")) {
                return order;
            }
        }
        return order;
    }

    private static void applyMembershipDiscount(List<Order> orders) {
        if (InputView.readMembership().equalsIgnoreCase("Y")) {
            for (Order order : orders) {
                order.setMembership();
            }
        }
    }

    public static void updateProductsQuantity(List<Order> orders) {
        for (Order order : orders) {
            int promotionQuantity = order.countPromotionQuantity();
            int nonePromotionQuantity = order.countNonePromotionQuantity();
            String productName = order.getNonePromotionProduct().getName();

            for (Product product : products) {
                if (product.getName().equals(productName) && product.getPromotion() == null) {
                    product.sell(nonePromotionQuantity);
                }
                if (product.getName().equals(productName) && product.getPromotion() != null) {
                    product.sell(promotionQuantity);
                }
            }
        }
    }
}
