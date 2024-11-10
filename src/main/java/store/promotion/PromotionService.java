package store.promotion;

import store.util.FileLoader;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionService {
    private static final PromotionService instance = new PromotionService();
    private static final PromotionRepository promotionRepository = PromotionRepository.getInstance();

    public static PromotionService getInstance() {
        return instance;
    }

    public void loadByMarkdown(String filePath) {
        List<String[]> records = FileLoader.loadFile(filePath);
        for (String[] record : records) {
            promotionRepository.add(new Promotion(record[0], Integer.parseInt(record[1]), Integer.parseInt(record[2]), LocalDateTime.parse(record[3]+"T00:00:00"), LocalDateTime.parse(record[4]+"T23:59:59")));
        }
    }

    public Promotion findByName(String promotionName) {
        return promotionRepository.findByName(promotionName);
    }
}
