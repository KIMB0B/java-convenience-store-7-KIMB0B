package store.util;

import store.Product;
import store.Promotion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    public static List<Promotion> loadPromitions(List<String[]> records) {
        List<Promotion> promotions = new ArrayList<>();

        for (String[] record : records) {
            promotions.add(new Promotion(record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), LocalDate.parse(record[3]), LocalDate.parse(record[4])));
        }

        return promotions;
    }

    public static List<Product> loadProducts(List<String[]> records, List<Promotion> promotions) {
        List<Product> products = new ArrayList<>();

        for (String[] record : records) {
            Promotion promotion = promotions.stream().filter(p -> p.getName().equals(record[3])).findFirst().orElse(null);
            products.add(new Product(record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), promotion));
        }

        products.sort((p1, p2) -> {
            if (p1.getName().equals(p2.getName()) && p1.getPromotion() == null) {
                return 1;
            }
            return p1.getName().compareTo(p2.getName());
        });

        return products;
    }
}
