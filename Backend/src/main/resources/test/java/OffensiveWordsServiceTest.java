

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OffensiveWordsServiceTest {

    private final OffensiveWordsService service = new OffensiveWordsService();

    @Test
    void shouldReturnFalseForNonOffensiveText() {
        assertFalse(service.offensiveWords("Hello, how are you?"));
    }

    @Test
    void shouldReturnTrueForTextContainingOffensiveWord() {
        assertTrue(service.offensiveWords("You are an idiot!"));
    }

    @Test
    void shouldReturnFalseForNullInput() {
        assertFalse(service.offensiveWords(null));
    }

    @Test
    void shouldDetectOffensiveWordsCaseInsensitive() {
        assertTrue(service.offensiveWords("You are STUPID"));
    }
}
