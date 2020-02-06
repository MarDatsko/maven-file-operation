import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Execute {
    private String mainPath = "src/main/resources/data.txt";
    private String smallWordsPath = "src/main/resources/smallWords.txt";
    private String content = readWithFile(mainPath);
    private static long smallWordsCounter = 0;
    private Set<String> badWords = new LinkedHashSet<>();
    private Map<String, Integer> goodWords = new TreeMap<>();

    public void run() {
        cleanText();
        System.out.println("The total number of words - " + countingTotalNumberOfWords());
        writeSmallWordsInAnotherArray();
        System.out.println("Quantity small words - " + smallWordsCounter);
        printMostFrequentlyWords();
        writeIntoFile();
    }

    private void printMostFrequentlyWords() {
        Integer max = 0;
        String str = null;
        for (var item : goodWords.entrySet()) {
            if (item.getValue() >= max) {
                max = item.getValue();
                str = item.getKey();
            }
        }
        System.out.println("The most frequently world is : " + str + " - " + goodWords.get(str));
    }

    private void writeSmallWordsInAnotherArray() {
        String[] splitString = splitString();
        for (var item : splitString) {
            if (item.length() < 3) {
                addBadWordsToArray(item);
            } else {
                addGoodWordsTOArray(item);
            }
        }
    }

    private void addGoodWordsTOArray(String item) {
        if (goodWords.containsKey(item)) {
            Integer i = 1 + goodWords.get(item);
            goodWords.put(item, i);
        } else {
            goodWords.put(item, 1);
        }
    }

    private void cleanText() {
        String extraCharacters = "[,()!]";
        content = content.replaceAll(extraCharacters, " ");
        content = content.replaceAll("\\s+|,\\s*|\\.\\s*", " ");
        content = content.toLowerCase();
    }

    private void addBadWordsToArray(String item) {
        smallWordsCounter++;
        badWords.add(item);
    }

    private String[] splitString() {
        return content.split(" ");
    }

    private Integer countingTotalNumberOfWords() {
        return splitString().length;
    }

    private String readWithFile(String path) {
        Path filePath = Paths.get(path);
        String content = null;
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private void writeIntoFile() {
        try {
            Files.write(Paths.get(smallWordsPath), badWords, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
