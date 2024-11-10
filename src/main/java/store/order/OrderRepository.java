package store.order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private static final OrderRepository instance = new OrderRepository();
    private static final List<Order> orders = new ArrayList<>();

    public static OrderRepository getInstance() {
        return instance;
    }

    public void add(Order order) {
        orders.add(order);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    public void clear() {
        orders.clear();
    }
}
