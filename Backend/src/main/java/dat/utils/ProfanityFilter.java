package dat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ProfanityFilter {

    private static final Set<String> BAD_WORDS = new HashSet<>();

    // Load the default filtered words once at startup
    static {
        useDefaultWordFilter();
    }

    public static boolean containsProfanity(String text) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (BAD_WORDS.contains(cleanWord)) {
                return true;
            }
        }
        return false;
    }

    public static void addWordsToFilter(Set<String> words) {
        words.forEach(String::toLowerCase);
        BAD_WORDS.addAll(words);
    }

    public static void removeWordsFromFilter(Set<String> words) {
        words.forEach(String::toLowerCase);
        BAD_WORDS.removeAll(words);
    }

    public static void useCustomWordFilter(Set<String> words) {
        BAD_WORDS.clear();
        addWordsToFilter(words);
    }

    public static void useDefaultWordFilter() {
        BAD_WORDS.clear();
        try (InputStream inputStream = ProfanityFilter.class.getClassLoader().getResourceAsStream("en_badwords.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                BAD_WORDS.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load bad words list", e);
        }
    }
}
