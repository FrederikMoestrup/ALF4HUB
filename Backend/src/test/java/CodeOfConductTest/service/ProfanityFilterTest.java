package CodeOfConductTest.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.ProfanityFilter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfanityFilterTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

        @Test
        @DisplayName("Tekst uden bandeord returnerer false")
        void testCleanText() {
            String input = "Hej med dig, alt er godt!";
            assertFalse(ProfanityFilter.containsProfanity(input));
        }

        @Test
        @DisplayName("Tekst med et bandeord returnerer true")
        void testWithBandeord() {
            String input = "Det her er noget lort.";
            assertTrue(ProfanityFilter.containsProfanity(input));
        }

        @Test
        @DisplayName("Tekst med flere bandeord returnerer true")
        void testMultipleBandeord() {
            String input = "PIK og FISSE!!!";
            assertTrue(ProfanityFilter.containsProfanity(input));
        }

        @Test
        @DisplayName("Edge case: tegnsætning og store bogstaver håndteres korrekt")
        void testWithPunctuationAndCase() {
            String input = "For fanden, det her spil er PIS!";
            assertTrue(ProfanityFilter.containsProfanity(input));
        }

        @Test
        @DisplayName("Skal returnere false hvis ingen bandeord er til stede")
        void testFalsePositive() {
            String input = "Jeg elsker spaghetti og kødsooooovs";
            assertFalse(ProfanityFilter.containsProfanity(input));
        }
    }