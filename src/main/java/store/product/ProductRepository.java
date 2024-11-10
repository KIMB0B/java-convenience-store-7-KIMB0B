package store.product;

import store.exception.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final ProductRepository instance = new ProductRepository();
    private final List<Product> products = new ArrayList<>();

    public static ProductRepository getInstance() {
        return instance;
    }

    public void add(Product product) {
        products.add(product);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    public List<Product> findByName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .toList();
    }

    public void reduceQuantity(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            ErrorHandler.quantityExceedsStockError();
        }
        product.setQuantity(product.getQuantity() - quantity);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}
