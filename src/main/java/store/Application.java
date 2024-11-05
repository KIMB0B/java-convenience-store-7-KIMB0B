package store;

import store.util.FileLoader;
import store.util.Loader;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;

public class Application {

    static final String promotionFilePath = "src/main/resources/promotions.md";
    static final String productFilePath = "src/main/resources/products.md";

    static List<Promotion> promotions = Loader.loadPromitions(FileLoader.loadFile(promotionFilePath));
    static List<Product> products = Loader.loadProducts(FileLoader.loadFile(productFilePath), promotions);

    public static void main(String[] args) {
        OutputView.printProducts(products);
        Map<String, Integer> buyingItems = InputView.readItem();

        boolean isMembership = InputView.readMembership().equalsIgnoreCase("Y");

        for (Map.Entry<String, Integer> item : buyingItems.entrySet()) {
            Order order = null;
            List<Product> matchProducts = products.stream().filter(p -> p.getName().equals(item.getKey())).toList();
            if (matchProducts.isEmpty()) {
                System.out.println("존재하지 않는 상품입니다.");
                return;
            }
            if (matchProducts.size() > 1 && matchProducts.get(0).getPromotion() == null) {
                return;
            }
            if (matchProducts.size() == 1) {
                order = new Order(matchProducts.getFirst(), item.getValue(), isMembership);
            }
            if (order == null) {
                order = new Order(matchProducts.get(0), matchProducts.get(1), item.getValue(), isMembership);
            }
        }
    }
}
