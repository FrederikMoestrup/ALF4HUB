package dat.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

public class OffensiveWordsCheck {

    private final List<String> offensiveWords;

    public OffensiveWordsCheck(String resourceFileName) {
        this.offensiveWords = loadWordsFromFile(resourceFileName);
    }

    private List<String> loadWordsFromFile(String resourceFileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourceFileName)) {
            if (input == null) {
                throw new RuntimeException("File not found: " + resourceFileName);
            }
            return new BufferedReader(new InputStreamReader(input))
                    .lines()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + resourceFileName, e);
        }
    }

    public Optional<String> findFirstOffensiveWord(String txt) {
        if (txt == null || txt.isBlank()) return Optional.empty();

        String lowerTxt = txt.toLowerCase();

        return offensiveWords.stream()
                .map(String::toLowerCase)
                .filter(word -> Pattern.compile("\\b" + Pattern.quote(word) + "\\b")
                        .matcher(lowerTxt)
                        .find())
                .findFirst();
    }

}
