package store.exception;

public class ErrorHandler {

    static final String ERROR_MESSAGE = "[ERROR] ";
    static final String INVALID_FORMAT = "올바르지 않은 형식으로 입력했습니다.";
    static final String NON_EXISTENT_PRODUCT = "존재하지 않는 상품입니다.";
    static final String QUANTITY_EXCEEDS_STOCK = "재고 수량을 초과하여 구매할 수 없습니다.";
    static final String GENERAL_INPUT = "잘못된 입력입니다.";
    static final String INVALID_STATE = "데이터가 잘못 저장되어 있습니다.";

    private static void throwIllegalArgumentException(String message) {
        throw new IllegalArgumentException(ERROR_MESSAGE + message + " 다시 입력해 주세요.");
    }

    private static void throwIllegalStateException(String message) {
        throw new IllegalStateException(ERROR_MESSAGE + message);
    }

    public static void invalidFormatError() {
        throwIllegalArgumentException(INVALID_FORMAT);
    }

    public static void nonExistentProductError() {
        throwIllegalArgumentException(NON_EXISTENT_PRODUCT);
    }

    public static void quantityExceedsStockError() {
        throwIllegalArgumentException(QUANTITY_EXCEEDS_STOCK);
    }

    public static void generalInputError() {
        throwIllegalArgumentException(GENERAL_INPUT);
    }

    public static void invalidStateError() {
        throwIllegalStateException(INVALID_STATE);
    }
}