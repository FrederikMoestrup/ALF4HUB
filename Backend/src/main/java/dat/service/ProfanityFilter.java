package dat.service;

import dat.enums.ProfanityWords;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ProfanityFilter {

    // Liste over alle bandeord fra enum, konverteret til små bogstaver
    private static final List<String> profanityWords = Arrays.stream(ProfanityWords.values())
            .map(word -> word.name().toLowerCase(Locale.ROOT)) // Locale.ROOT: Ignorer sprog og brug en neutral/global konvertering
            .toList();

    // Tjekker om en given tekst indeholder nogen af de definerede bandeord.
    //Parameter "text" Den tekst der skal undersøges
    //Returnerer true hvis teksten indeholder bandeord, ellers false
    public static boolean containsProfanity(String text) {
        String[] words = text.toLowerCase(Locale.ROOT).split("\\s+"); // .split("\\s+") → del teksten op i ord, hvor der er mellemrum.
        for (String word : words) {
            word = word.replaceAll("[^a-zæøå]", ""); // fjerner specialtegn
            if (profanityWords.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
