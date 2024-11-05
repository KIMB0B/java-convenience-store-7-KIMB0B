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
    }
}
