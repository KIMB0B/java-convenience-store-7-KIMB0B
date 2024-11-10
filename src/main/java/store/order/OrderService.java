package store.order;

import store.exception.ErrorHandler;
import store.product.Product;
import store.product.ProductService;
import store.view.InputView;

import java.util.List;
import java.util.Map;

public class OrderService {
    private static final ProductService productService = ProductService.getInstance();
    private static final OrderRepository orderRepository = OrderRepository.getInstance();
    private static final OrderService instance = new OrderService();

    public static OrderService getInstance() {
        return instance;
    }

    public void addOrdersByItemMap(Map<String, Integer> buyingItems) {
        for (Map.Entry<String, Integer> item : buyingItems.entrySet()) {
            List<Product> matchingProducts = productService.findProductsByName(item.getKey());
            validateMatchingProducts(matchingProducts, item.getValue());
            orderRepository.add(createOrder(matchingProducts, item.getValue()));
        }

        applyMembershipDiscount();
    }

    public void validateMatchingProducts(List<Product> matchingProducts, int quantity) {
        if (matchingProducts.isEmpty()) {
            ErrorHandler.nonExistentProductError();
        }
        if (matchingProducts.size() > 1 && matchingProducts.getFirst().getPromotion() == null) {
            ErrorHandler.generalInputError();
        }
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void clear() {
        orderRepository.clear();
    }

    private Order createOrder(List<Product> matchingProducts, int quantity) {
        if (matchingProducts.size() == 1) {
            return createSingleOrder(matchingProducts.getFirst(), quantity);
        }
        return createPromotionOrder(matchingProducts, quantity);
    }

    private void applyMembershipDiscount() {
        if (InputView.readMembership().equalsIgnoreCase("Y")) {
            for (Order order : orderRepository.findAll()) {
                order.setMembership();
            }
        }
    }

    private Order createSingleOrder(Product product, int quantity) {
        Order order = new Order(product, quantity);
        product.sell(order.countNonePromotionQuantity());
        return order;
    }

    private static Order createPromotionOrder(List<Product> matchingProducts, int quantity) {
        Order order = new Order(matchingProducts.get(0), matchingProducts.get(1), quantity);
        handleAdditionalItems(order, matchingProducts.get(0).getName());
        handleNonPromotionalItems(order, matchingProducts.get(0).getName());

        return order;
    }

    private static void handleAdditionalItems(Order order, String productName) {
        int additionalItems = order.canRecieveItem();
        if (additionalItems > 0) {
            boolean wantsAdditionalItems = InputView.readBuyMore(productName, additionalItems).equalsIgnoreCase("Y");
            if (wantsAdditionalItems) {
                order.addQuantity(additionalItems);
            }
        }
    }

    private static void handleNonPromotionalItems(Order order, String productName) {
        int nonPromotionalItems = order.cantPromotionQuantity();
        if (nonPromotionalItems > 0) {
            boolean wantsNonPromotionalItems = InputView.readCantPromotion(productName, nonPromotionalItems).equalsIgnoreCase("Y");
            if (!wantsNonPromotionalItems) {
                order.removeQuantity(nonPromotionalItems);
            }
        }
    }
}
