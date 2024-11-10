package store.util;

import store.Product;
import store.Promotion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    public static List<Promotion> loadPromitions(List<String[]> records) {
        List<Promotion> promotions = new ArrayList<>();

        for (String[] record : records) {
            promotions.add(new Promotion(record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), LocalDateTime.parse(record[3]+"T00:00:00"), LocalDateTime.parse(record[4]+"T23:59:59")));
        }

        return promotions;
    }

    public static List<Product> loadProducts(List<String[]> records, List<Promotion> promotions) {
        List<Product> products = new ArrayList<>();
        Product beforeProduct = new Product("", 0, 0, null);

        for (String[] record : records) {
            Promotion promotion = findPromotionByName(promotions, record[2]);
            addProduct(products, record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), promotion, beforeProduct);
            beforeProduct.setProduct(record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), promotion);
        }
        checkLastRecord(products, promotions, records.getLast());

        return products;
    }

    private static void addProduct(List<Product> products, String name, int price, int quantity, Promotion promotion, Product beforeProduct) {
        if (beforeProduct.getPromotion() != null && !beforeProduct.getName().equals(name)) {
            products.add(new Product(beforeProduct.getName(), beforeProduct.getPrice(), 0, null));
        }

        products.add(new Product(name, price, quantity, promotion));
    }

    private static Promotion findPromotionByName(List<Promotion> promotions, String promotionName) {
        return promotions.stream()
                .filter(p -> p.getName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }

    private static void checkLastRecord(List<Product> products, List<Promotion> promotions, String[] lastRecord) {
        if (findPromotionByName(promotions, lastRecord[2]) != null) {
            products.add(new Product(lastRecord[0], Integer.parseInt(lastRecord[1]), 0, null));
        }
    }
}
