package store.view;

import camp.nextstep.edu.missionutils.Console;

import java.util.LinkedHashMap;
import java.util.Map;

public class InputView {

    private static final String ITEM_INPUT_PROMPT = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String MEMBERSHIP_PROMPT = "멤버십 할인을 받으시겠습니까?";
    private static final String BUY_MORE_PROMPT = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까?";
    private static final String ONE_MORE_PROMPT = "감사합니다. 구매하고 싶은 다른 상품이 있나요?";
    private static final String CANT_PROMOTION_PROMPT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까?";

    public static Map<String, Integer> readItem() {
        System.out.println(ITEM_INPUT_PROMPT);
        String input = Console.readLine();

        Map<String, Integer> items = new LinkedHashMap<>();

        for (String item : input.split(",")) {
            parseAndPutItems(items, item);
        }
        System.out.println();
        return items;
    }

    public static String readMembership() {
        return readYesNoInput(MEMBERSHIP_PROMPT);
    }

    public static String readBuyMore(String name, int count) {
        return readYesNoInput(String.format(BUY_MORE_PROMPT, name, count));
    }

    public static String readOneMore() {
        return readYesNoInput(ONE_MORE_PROMPT);
    }

    public static String readCantPromotion(String name, int count) {
        return readYesNoInput(String.format(CANT_PROMOTION_PROMPT, name, count));
    }

    private static String readYesNoInput(String prompt) {
        System.out.println(prompt + " (Y/N)");
        String input = Console.readLine().trim();
        System.out.println();
        return input;
    }

    private static void parseAndPutItems(Map<String, Integer> items, String item) {
        String[] itemInfo = item.split("-");
        String itemName = itemInfo[0];
        String itemQuantity = itemInfo[1];
        if (itemName.startsWith("[") && itemQuantity.endsWith("]")) {
            itemName = itemName.substring(1);
            itemQuantity = itemQuantity.substring(0, itemQuantity.length() - 1);
        }
        items.put(itemName, Integer.parseInt(itemQuantity));
    }
}
