package dat.services;

import java.util.List;

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
