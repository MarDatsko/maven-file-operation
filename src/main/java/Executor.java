import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Executor {
    private final String pathToFile = "src/main/resources/data.txt";
    private final String pathToSmallWordsFile = "src/main/resources/smallWords.txt";
    private final int minWordLength = 3;
    private String content = read(pathToFile);
    private static long wordToAmount = 0;
    private Set<String> smallWords = new LinkedHashSet<>();
    private Map<String, Integer> goodWords = new TreeMap<>();

    public void run() {
        cleanText();

        printTotalNumberOfWords();

        writeSmallWordsInAnotherArray();

        System.out.println("Quantity small words - " + wordToAmount);

        printMostFrequentlyWords();

        write(pathToSmallWordsFile);
    }

    private void printMostFrequentlyWords() {
        Integer max = 0;
        String str = null;
        //Integer integer = goodWords.entrySet().stream().map(Map.Entry::getValue).max(Integer::compareTo).get();
        for (var item : goodWords.entrySet()) {
            if (item.getValue() >= max) {
                max = item.getValue();
                str = item.getKey();
            }
        }
        System.out.println("The most frequently world is : " + str + " - " + goodWords.get(str));
    }

    private void writeSmallWordsInAnotherArray() {
        String[] stringArray = splitString();
        for (var item : stringArray) {
            if (item.length() < minWordLength) {
                addSmallWordsToArray(item);
            } else {
                addGoodWordsToArray(item);
            }
        }
    }

    private void addGoodWordsToArray(String word) {
        if (goodWords.containsKey(word)) {
            Integer wordAmount = goodWords.get(word);
            goodWords.put(word, ++wordAmount);
        } else {
            goodWords.put(word, 1);
        }
    }

    private void cleanText() {
        String extraCharacters = "[,()!]";
        content = content.replaceAll(extraCharacters, " ");
        content = content.replaceAll("\\s+|,\\s*|\\.\\s*", " ");
        content = content.toLowerCase();
    }

    private void addSmallWordsToArray(String item) {
        wordToAmount++;
        smallWords.add(item);
    }

    private String[] splitString() {
        return content.split(" ");
    }

    private void printTotalNumberOfWords() {
        System.out.println("The total number of words - " + content.split(" ").length);
    }

    private String read(String path) {
        Path filePath = Paths.get(path);
        String content = null;

        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    private void write(String path) {
        Path filePath = Paths.get(path);

        try {
            Files.write(filePath, smallWords, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
