package store.product;

import store.order.Order;
import store.order.OrderService;
import store.promotion.Promotion;
import store.promotion.PromotionService;
import store.util.FileLoader;

import java.util.List;

public class ProductService {
    private static final ProductService instance = new ProductService();
    private static final PromotionService promotionService = PromotionService.getInstance();
    private static final OrderService orderService = OrderService.getInstance();
    private static final ProductRepository productRepository = ProductRepository.getInstance();

    public static ProductService getInstance() {
        return instance;
    }

    public void loadByMarkdown(String filePath) {
        Product beforeProduct = new Product("", 0, 0, null);
        List<String[]> records = FileLoader.loadFile(filePath);

        for (String[] record : records) {
            Promotion promotion = promotionService.findByName(record[3]);
            addWithBeforeProductChecking(new Product(record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), promotion), beforeProduct);
            beforeProduct.setProduct(record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), promotion);
        }
        checkLastRecord(records.getLast());
    }

    public List<Product> findALl() {
        return productRepository.findAll();
    }

    public List<Product> findProductsByName(String productName) {
        return productRepository.findByName(productName);
    }

    public void updateProductsQuantity() {
        for (Order order : orderService.findAll()) {
            for (Product product : findProductsByName(order.getNonePromotionProduct().getName())) {
                updateProductQuantity(product, order.countPromotionQuantity(), order.countNonePromotionQuantity());
            }
        }
    }

    private void updateProductQuantity(Product product, int promotionQuantity, int nonePromotionQuantity) {
        if (product.getPromotion() == null) {
            productRepository.reduceQuantity(product, nonePromotionQuantity);
            return;
        }
        productRepository.reduceQuantity(product, promotionQuantity);
    }

    private void addWithBeforeProductChecking(Product product, Product beforeProduct) {
        if (beforeProduct == null) {
            productRepository.add(product);
            return;
        }
        if (beforeProduct.getPromotion() != null && !beforeProduct.getName().equals(product.getName())) {
            productRepository.add(new Product(beforeProduct.getName(), beforeProduct.getPrice(), 0, null));
        }
        productRepository.add(product);
    }

    private void checkLastRecord(String[] lastRecord) {
        if (promotionService.findByName(lastRecord[3]) != null) {
            productRepository.add(new Product(lastRecord[0], Integer.parseInt(lastRecord[1]), 0, null));
        }
    }
}
