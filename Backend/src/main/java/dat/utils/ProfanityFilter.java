package dat.utils;

import dat.dtos.BlogPostDTO;

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

    public static BlogPostDTO returnCensoredBlogPost(BlogPostDTO blogPostDTO) {
        blogPostDTO.setTitle(censorText(blogPostDTO.getTitle()));
        blogPostDTO.setContent(censorText(blogPostDTO.getContent()));
        return blogPostDTO;
    }

    private static String censorText(String text) {
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            // Strip punctuation from word for comparison
            String prefix = word.replaceAll("^[^a-zA-Z]+", ""); // non-letter at start
            String suffix = word.replaceAll("[a-zA-Z]+", "");   // non-letter at end
            String coreWord = word.replaceAll("[^a-zA-Z]", ""); // only letters

            String censoredWord = BAD_WORDS.contains(coreWord.toLowerCase())
                    ? "#".repeat(coreWord.length())
                    : coreWord;

            // Reconstruct the word with original punctuation
            result.append(word.replace(coreWord, censoredWord));
            if (i < words.length - 1) result.append(" ");
        }
        return result.toString();
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
