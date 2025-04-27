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
        blogpost.setContent("shit and a ton of kittens also i want to say fuck");
        ProfanityFilter.addWordsToFilter(Set.of("kittens"));
        ProfanityFilter.removeWordsFromFilter(Set.of("fuck"));
        String test = ProfanityFilter.censorText(blogpost.getContent());
        System.out.println("raw content: " + blogpost.getContent());
        System.out.println("filtered content: " + test);
        System.out.println(ProfanityFilter.containsProfanity(blogpost.getContent()));
    }

    private static final Set<String> BAD_WORDS = new HashSet<>();

    // Load the bad words once at startup
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

    public static void addWordsToFilter(Set<String> words) {
        BAD_WORDS.addAll(words);
    }

    public static void removeWordsFromFilter(Set<String> words) {
        BAD_WORDS.removeAll(words);
    }

    public static void useCustomWordFilter(Set<String> words) {
        BAD_WORDS.clear();
        addWordsToFilter(words);
    }

    public static void useDefaultWordFilter() {
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
