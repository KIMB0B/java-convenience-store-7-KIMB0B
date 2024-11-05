package store.view;

import camp.nextstep.edu.missionutils.Console;

import java.util.LinkedHashMap;
import java.util.Map;

public class InputView {
    public static Map<String, Integer> readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();

        Map<String, Integer> items = new LinkedHashMap<>();
        for (String item : input.split(",")) {
            String[] itemInfo = item.split("-");
            String itemName = itemInfo[0];
            String itemQuantity = itemInfo[1];
            if (itemName.startsWith("[") && itemQuantity.endsWith("]")) {
                itemName = itemName.substring(1);
                itemQuantity = itemQuantity.substring(0, itemQuantity.length() - 1);
            }
            items.put(itemName, Integer.parseInt(itemQuantity));
        }
        System.out.println();
        return items;
    }

    public static String readMembership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        String input = Console.readLine().trim();
        System.out.println();
        return input;
    }

    public static String readOneMore() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String input = Console.readLine().trim();
        System.out.println();
        return input;
    }
}
