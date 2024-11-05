package store;

import store.util.FileLoader;
import store.util.Loader;
import store.view.OutputView;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        final String promotionFilePath = "src/main/resources/promotions.md";
        final String productFilePath = "src/main/resources/products.md";

        List<Promotion> promotions = Loader.loadPromitions(FileLoader.loadFile(promotionFilePath));
        List<Product> products = Loader.loadProducts(FileLoader.loadFile(productFilePath), promotions);

        OutputView.printProducts(products);
    }
}
