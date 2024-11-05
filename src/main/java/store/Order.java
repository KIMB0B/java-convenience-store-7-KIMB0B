package store;

import camp.nextstep.edu.missionutils.DateTimes;

public class Order {
    private Product promotionProduct = null;
    private final Product nonePromotionProduct;
    private int quantity;
    private final boolean isMembership;

    public Order(Product nonePromotionProduct, int quantity, boolean isMembership) {
        this.nonePromotionProduct = nonePromotionProduct;
        this.quantity = quantity;
        this.isMembership = isMembership;
    }

    public Order(Product promotionProduct, Product nonePromotionProduct, int quantity, boolean isMembership) {
        this.promotionProduct = promotionProduct;
        this.nonePromotionProduct = nonePromotionProduct;
        this.quantity = quantity;
        this.isMembership = isMembership;
    }

    public Product getPromotionProduct() {
        return promotionProduct;
    }

    public Product getNonePromotionProduct() {
        return nonePromotionProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public int countPromotionQuantity() {
        if (promotionProduct == null) {
            return 0;
        }
        if (!promotionProduct.getPromotion().isPromotion(DateTimes.now())) {
            return 0;
        }
        int minValue = Math.min(this.promotionProduct.getQuantity(), this.quantity);
        return minValue / (promotionProduct.getPromotion().getGet() + promotionProduct.getPromotion().getBuy()) * (promotionProduct.getPromotion().getBuy() + promotionProduct.getPromotion().getGet());
    }

    public int countFreeItem() {
        if (promotionProduct == null) {
            return 0;
        }
        return countPromotionQuantity() / (promotionProduct.getPromotion().getBuy() + promotionProduct.getPromotion().getGet()) * promotionProduct.getPromotion().getGet();
    }

    public int canRecieveItem() {
        if (this.quantity - countPromotionQuantity() > 0) {
            return promotionProduct.getPromotion().getGet();
        }
        return 0;
    }

    public int countNonePromotionQuantity() {
        return this.quantity - countPromotionQuantity();
    }

    public int promotionDiscount() {
        if (promotionProduct == null) {
            return 0;
        }
        return countFreeItem() * promotionProduct.getPrice();
    }

    public int membershipDiscount() {
        if (!isMembership) {
            return 0;
        }
        return (int) (nonePromotionProduct.getPrice() * countNonePromotionQuantity() * 0.3);
    }

    public int totalUseMoney() {
        return this.quantity * this.nonePromotionProduct.getPrice();
    }

    public int calculatePrice() {
        return totalUseMoney() - promotionDiscount() - membershipDiscount();
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
