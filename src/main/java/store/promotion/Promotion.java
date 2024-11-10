package store.promotion;

import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDateTime start_date;
    private final LocalDateTime end_date;

    public Promotion(String name, int buy, int get, LocalDateTime start_date, LocalDateTime end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Object getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public boolean isPromotion(LocalDateTime date) {
        return date.isAfter(start_date) && date.isBefore(end_date);
    }
}
