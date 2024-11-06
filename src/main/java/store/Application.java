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

            boolean isMembership = InputView.readMembership().equalsIgnoreCase("Y");

            List<Order> orders = setOrders(buyingItems, isMembership);

            OutputView.printReceipt(orders);

            sellProduct(orders);

            if (oneMore == true) {
                oneMore = InputView.readOneMore().equalsIgnoreCase("Y");
            }
        }
    }

    public static List<Order> setOrders(Map<String, Integer> buyingItems, boolean isMembership) {
        List<Order> orders = new ArrayList<>();

        for (Map.Entry<String, Integer> item : buyingItems.entrySet()) {
            List<Product> matchProducts = products.stream().filter(p -> p.getName().equals(item.getKey())).toList();
            if (matchProducts.isEmpty()) {
                return null;
            }
            if (matchProducts.size() > 1 && matchProducts.get(0).getPromotion() == null) {
                return null;
            }
            orders.add(setOrder(matchProducts, item.getValue(), isMembership));
        }
        return orders;
    }

    public static Order setOrder(List<Product> matchProducts, int quantity, boolean isMembership) {
        if (matchProducts.size() == 1) {
            Order order = new Order(matchProducts.get(0), quantity, isMembership);
            for (Product product : products) {
                if (product.getName().equals(matchProducts.get(0).getName())) {
                    product.sell(order.countNonePromotionQuantity());
                }
            }
            return order;
        }
        Order order = new Order(matchProducts.get(0), matchProducts.get(1), quantity, isMembership);
        if (order.canRecieveItem() > 0) {
            if (InputView.readBuyMore(matchProducts.getFirst().getName(), order.canRecieveItem()).equals("N")) {
                return order;
            }
            order.addQuantity(order.canRecieveItem());
        }
        if (order.cantPromotionQuantity() > 0) {
            if (InputView.readCantPromotion(matchProducts.getFirst().getName(), order.cantPromotionQuantity()).equals("Y")) {
                return order;
            }
        }
        return order;
    }

    public static void sellProduct(List<Order> orders) {

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
