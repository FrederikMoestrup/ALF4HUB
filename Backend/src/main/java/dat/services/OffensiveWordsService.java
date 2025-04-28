package dat.services;

import java.util.List;


/**
 * Service class that manages the business logic.
 * Checks a list of offensive words, ignoring case (both lowercase and uppercase).
 */

public class OffensiveWordsService {

    private final List<String> offensiveWords = List.of("idiot", "fuck", "stupid", "retard", "pussy");

    public boolean offensiveWords(String txt) {
        if(txt == null) {
            return false;
        } else {
            return offensiveWords.stream()
                    .anyMatch(words -> txt.toLowerCase().contains(words.toLowerCase()));

        }
    }

}
