import java.util.*;
import java.io.*;

public class TagExtractor {
    private Map<String, Integer> tagFrequencyMap;
    private Set<String> stopWords;

    public TagExtractor() {
        this.tagFrequencyMap = new HashMap<>();
        this.stopWords = new HashSet<>();
    }

    public void loadStopWords(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            stopWords.add(line.trim().toLowerCase());
        }

        reader.close();
    }

    public void processText(String text) {
        String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        for (String word : words) {
            if (!stopWords.contains(word)) {
                tagFrequencyMap.put(word, tagFrequencyMap.getOrDefault(word, 0) + 1);
            }
        }
    }

    public Map<String, Integer> getTagFrequencyMap() {
        return tagFrequencyMap;
    }

    public void setStopWords(Set<String> stopWords) {
        this.stopWords = stopWords;
    }
}
