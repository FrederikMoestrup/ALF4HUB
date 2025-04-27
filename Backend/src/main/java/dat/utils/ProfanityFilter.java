package dat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ProfanityFilter {

    private static final Set<String> BAD_WORDS = new HashSet<>();

    // load the default filtered words once at startup
    static {
//        try (InputStream inputStream = ProfanityFilter.class.getClassLoader().getResourceAsStream("en_badwords.txt");
//             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                BAD_WORDS.add(line.trim().toLowerCase());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to load bad words list", e);
//        }
        useDefaultWordFilter();
    }

    public static String censorText(String text) {
        // replaces every char of a filtered word with * and returns a censored string
        String[] words = text.split("\\s+");
        StringBuilder censoredText = new StringBuilder();

        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase(); // Remove punctuation for matching
            if (BAD_WORDS.contains(cleanWord)) {
                censoredText.append("*".repeat(cleanWord.length()));
            } else {
                censoredText.append(word);
            }
            censoredText.append(" ");
        }

        return censoredText.toString().trim();
    }

    public static boolean containsProfanity(String text) {
        // easily lets you detect if a string contains filtered words
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
        // allows you to add certain words to the filter
        words.forEach(String::toLowerCase);
        BAD_WORDS.addAll(words);
    }

    public static void removeWordsFromFilter(Set<String> words) {
        // allows you to remove certain words from the filter
        words.forEach(String::toLowerCase);
        BAD_WORDS.removeAll(words);
    }

    public static void useCustomWordFilter(Set<String> words) {
        // replace the entire default list of words to filter with your own set
        BAD_WORDS.clear();
        addWordsToFilter(words);
    }

    public static void useDefaultWordFilter() {
        // resets the filter back to default
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
