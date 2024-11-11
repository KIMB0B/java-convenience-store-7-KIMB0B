package store.order;

import camp.nextstep.edu.missionutils.DateTimes;
import store.product.Product;

public class Order {
    private Product promotionProduct = null;
    private final Product nonePromotionProduct;
    private int quantity;
    private boolean isMembership = false;

    public Order(Product nonePromotionProduct, int quantity) {
        this.nonePromotionProduct = nonePromotionProduct;
        this.quantity = quantity;
    }

    public Order(Product promotionProduct, Product nonePromotionProduct, int quantity) {
        this.promotionProduct = promotionProduct;
        this.nonePromotionProduct = nonePromotionProduct;
        this.quantity = quantity;
    }

    public Product getNonePromotionProduct() {
        return nonePromotionProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public int promotionalQuantityCount() {
        if (promotionProduct == null) {
            return 0;
        }
        if (!promotionProduct.getPromotion().isPromotion(DateTimes.now())) {
            return 0;
        }
        int minValue = Math.min(this.promotionProduct.getQuantity(), this.quantity);
        return minValue / (promotionProduct.getPromotion().getGet() + promotionProduct.getPromotion().getBuy()) * (promotionProduct.getPromotion().getBuy() + promotionProduct.getPromotion().getGet());
    }

    public int nonePromotionalQuantityCount() {
        if (promotionProduct == null) {
            return 0;
        }
        if (!promotionProduct.getPromotion().isPromotion(DateTimes.now())) {
            return 0;
        }
        return this.quantity - promotionalQuantityCount();
    }

    public int freeItemCount() {
        if (promotionProduct == null) {
            return 0;
        }
        return promotionalQuantityCount() / (promotionProduct.getPromotion().getBuy() + promotionProduct.getPromotion().getGet()) * promotionProduct.getPromotion().getGet();
    }

    public int additionalItemCount() {
        if ((this.quantity + promotionProduct.getPromotion().getGet()) % (promotionProduct.getPromotion().getBuy() + promotionProduct.getPromotion().getGet()) == 0) {
            return promotionProduct.getPromotion().getGet();
        }
        return 0;
    }

    public int promotionDiscountPrice() {
        if (promotionProduct == null) {
            return 0;
        }
        return freeItemCount() * promotionProduct.getPrice();
    }

    public int membershipDiscountPrice() {
        if (!isMembership) {
            return 0;
        }
        return (int) (nonePromotionProduct.getPrice() * nonePromotionalQuantityCount() * 0.3);
    }

    public int totalUsePrice() {
        return this.quantity * this.nonePromotionProduct.getPrice();
    }

    public int finalCalculatePrice() {
        return totalUsePrice() - promotionDiscountPrice() - membershipDiscountPrice();
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void setMembership() {
        this.isMembership = true;
    }
}
