package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    public static List<String[]> loadFile(String filePath) {
        List<String[]> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                result.add(line.split(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
