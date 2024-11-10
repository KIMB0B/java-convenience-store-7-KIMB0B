package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    public static List<String[]> loadFile(String filePath) {
        List<String[]> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // 첫 줄 필드명은 읽지 않음
            readLines(br, result);
        } catch (IOException e) {
            handleException(e);
        }
        return result;
    }

    private static void readLines(BufferedReader br, List<String[]> result) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            result.add(line.split(","));
        }
    }

    private static void handleException(IOException e) {
        e.printStackTrace();
    }
}