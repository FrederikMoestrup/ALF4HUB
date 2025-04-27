package dat.utils;

import dat.entities.BlogPost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProfanityFilterTest {

    private static BlogPost blogpost1 = new BlogPost();
    private static BlogPost blogpost2 = new BlogPost();

    @BeforeAll
    static void setupClass() {
        blogpost1.setContent("i want to type the word fuck");
        blogpost2.setContent("i really hate the word pineapple");
    }

    @AfterEach
    void tearDown() {
        ProfanityFilter.useDefaultWordFilter();
    }

    @Test
    void censorText() {
        // no idea how to really test this, it replaces words like "shit" with "****"
    }

    @Test
    void containsProfanity() {
        Boolean check1;
        Boolean check2;

        // given: i want to check if a post contains profanity/filtered word(s)

        // when: i check if either of the blogposts contains a filtered word
        check1 = ProfanityFilter.containsProfanity(blogpost1.getContent());
        check2 = ProfanityFilter.containsProfanity(blogpost2.getContent());

        // then: only check1 should return true, since blogpost1 contains a word that is filtered by default
        assertTrue(check1);
        assertFalse(check2);
    }

    @Test
    void addWordsToFilter() {
        Boolean check1;
        Boolean check2;

        // given: i add word(s) to the filter
        ProfanityFilter.addWordsToFilter(Set.of("pineapple"));

        // when: i check if either of the blogposts contains a filtered word
        check1 = ProfanityFilter.containsProfanity(blogpost1.getContent());
        check2 = ProfanityFilter.containsProfanity(blogpost2.getContent());

        // then: both check1 and check2 should return true, due to blogpost2 now containing a filtered word
        assertTrue(check1);
        assertTrue(check2);
    }

    @Test
    void removeWordsFromFilter() {
        Boolean check1;
        Boolean check2;

        // given: i remove word(s) from the filter
        ProfanityFilter.removeWordsFromFilter(Set.of("fuck"));

        // when: i check if either of the blogposts contains a filtered word
        check1 = ProfanityFilter.containsProfanity(blogpost1.getContent());
        check2 = ProfanityFilter.containsProfanity(blogpost2.getContent());

        // then: both check1 and check2 should return false, since blostpost1 no longer contains a filtered word
        assertFalse(check1);
        assertFalse(check2);
    }

    @Test
    void useCustomWordFilter() {
        Boolean check1;
        Boolean check2;

        // given: i use a custom word filter which only includes "really" and "hate"
        ProfanityFilter.useCustomWordFilter(Set.of("really", "hate"));

        // when: i check if either of the blogposts contains a filtered word
        check1 = ProfanityFilter.containsProfanity(blogpost1.getContent());
        check2 = ProfanityFilter.containsProfanity(blogpost2.getContent());

        // then: only check2 should return true, as only blogpost2 contains the filtered words
        assertFalse(check1);
        assertTrue(check2);
    }

    @Test
    void useDefaultWordFilter() {
        // this method just switches the filter back to default
    }
}