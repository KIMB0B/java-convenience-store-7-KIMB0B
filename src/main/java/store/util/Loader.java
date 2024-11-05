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

        String beforeName = "";
        int beforePrice = 0;
        Promotion beforePromotion = null;

        for (String[] record : records) {
            if (beforePromotion != null && !beforeName.equals(record[0])) {
                products.add(new Product(beforeName, beforePrice, 0, null));
            }
            Promotion promotion = promotions.stream().filter(p -> p.getName().equals(record[3])).findFirst().orElse(null);
            products.add(new Product(record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), promotion));
            if (record == records.get(records.size() - 1) && promotion != null) {
                products.add(new Product(record[0], Integer.parseInt(record[1]), 0, null));
                return products;
            }
            beforeName = record[0];
            beforePrice = Integer.parseInt(record[1]);
            beforePromotion = promotion;
        }

        return products;
    }
}
