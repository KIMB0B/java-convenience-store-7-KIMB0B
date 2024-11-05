package store;

public class Order {
    private Product promotionPruoduct = null;
    private final Product nonePromotionProducts;
    private final int quantity;
    private final boolean isMembership;

    public Order(Product nonePromotionProducts,int quantity, boolean isMembership) {
        this.nonePromotionProducts = nonePromotionProducts;
        this.quantity = quantity;
        this.isMembership = isMembership;
    }

    public Order(Product promotionPruoduct, Product nonePromotionProducts, int quantity, boolean isMembership) {
        this.promotionPruoduct = promotionPruoduct;
        this.nonePromotionProducts = nonePromotionProducts;
        this.quantity = quantity;
        this.isMembership = isMembership;
    }

    public Product getNonePromotionProducts() {
        return nonePromotionProducts;
    }

    public int getQuantity() {
        return quantity;
    }

    public int countPromotionQuantity() {
        if (promotionPruoduct == null) {
            return 0;
        }
        int minValue = Math.min(this.promotionPruoduct.getQuantity(), this.quantity);
        return minValue / (promotionPruoduct.getPromotion().getGet() + promotionPruoduct.getPromotion().getBuy()) * (promotionPruoduct.getPromotion().getBuy() + promotionPruoduct.getPromotion().getGet());
    }

    public int countFreeItem() {
        if (promotionPruoduct == null) {
            return 0;
        }
        return countPromotionQuantity() / (promotionPruoduct.getPromotion().getBuy() + promotionPruoduct.getPromotion().getGet()) * promotionPruoduct.getPromotion().getGet();
    }

    public int countNonePromotionQuantity() {
        return Math.max(0, this.quantity - countPromotionQuantity());
    }

    public int promotionDiscount() {
        if (promotionPruoduct == null) {
            return 0;
        }
        return countFreeItem() * promotionPruoduct.getPrice();
    }

    public int membershipDiscount() {
        if (!isMembership) {
            return 0;
        }
        return (int) (nonePromotionProducts.getPrice() * countNonePromotionQuantity() * 0.3);
    }

    public int totalUseMoney() {
        return this.quantity * this.nonePromotionProducts.getPrice();
    }

    public int calculatePrice() {
        return totalUseMoney() - promotionDiscount() - membershipDiscount();
    }
}
