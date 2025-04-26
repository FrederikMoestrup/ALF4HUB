package dat.utils;

import dat.entities.BlogPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ProfanityFilter {

    public static void main(String[] args) {
        BlogPost blogpost = new BlogPost();
        blogpost.setContent("shit and a ton og kittens");
        String test = ProfanityFilter.censorText(blogpost.getContent());
        System.out.println(blogpost.getContent());
        System.out.println(test);
        System.out.println(ProfanityFilter.containsProfanity(blogpost.getContent()));
    }

    private static final Set<String> BAD_WORDS = new HashSet<>();

    // Load the bad words once at startup
    static {
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

    public static String censorText(String text) {
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
        String[] words = text.split("\\s+");

        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (BAD_WORDS.contains(cleanWord)) {
                return true;
            }
        }
        return false;
    }
}
