package store.exception;

public class ErrorHandler {

    static final String ERROR_MESSAGE = "[ERROR] ";
    static final String INVALID_FORMAT = "올바르지 않은 형식으로 입력했습니다.";
    static final String NON_EXISTENT_PRODUCT = "존재하지 않는 상품입니다.";
    static final String QUANTITY_EXCEEDS_STOCK = "재고 수량을 초과하여 구매할 수 없습니다.";
    static final String GENERAL_INPUT = "잘못된 입력입니다.";

    private static void printErrorMessage(String message) {
        throw new IllegalArgumentException(ERROR_MESSAGE + message + " 다시 입력해 주세요.");
    }

    public static void invalidFormatError() {
        printErrorMessage(INVALID_FORMAT);
    }

    public static void nonExistentProductError() {
        printErrorMessage(NON_EXISTENT_PRODUCT);
    }

    public static void quantityExceedsStockError() {
        printErrorMessage(QUANTITY_EXCEEDS_STOCK);
    }

    public static void generalInputError() {
        printErrorMessage(GENERAL_INPUT);
    }
}