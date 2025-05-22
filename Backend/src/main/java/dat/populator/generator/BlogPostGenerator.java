package dat.populator.generator;

import dat.entities.BlogPost;
import dat.enums.BlogPostStatus;
import dat.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlogPostGenerator {
    private final List<User> users;
    private final int postsPerUser;
    private final Random random;

    private static final String[] SAMPLE_TITLES = {
            "Tips for Climbing Ranked", "Rocket League Pro Moves", "CS:GO Loadouts Guide",
            "Esports Psychology", "Top 10 Gaming Tactics", "Solo Queue Success"
    };

    private static final String[] SAMPLE_CONTENTS = {
            "This is a deep dive into strategic movement and rotations.",
            "Learn how to master aerial shots and dribbling.",
            "Optimize your weapon choices based on the current meta.",
            "Understanding mindset and focus during tournaments.",
            "Secrets from pro players on map control and communication.",
            "How to consistently win 1v1s and carry your team."
    };

    public BlogPostGenerator(List<User> users, int postsPerUser, Random random) {
        this.users = users;
        this.postsPerUser = postsPerUser;
        this.random = random;
    }

    public List<BlogPost> generate() {
        List<BlogPost> blogPosts = new ArrayList<>();

        for (User user : users) {
            for (int i = 0; i < postsPerUser; i++) {
                BlogPost post = BlogPost.builder()
                        .user(user)
                        .title(randomTitle())
                        .content(randomContent())
                        .createdAt(LocalDateTime.now().minusDays(random.nextInt(30)))
                        .updatedAt(LocalDateTime.now())
                        .status(randomStatus())
                        .build();
                blogPosts.add(post);
            }
        }

        return blogPosts;
    }

    private String randomTitle() {
        return SAMPLE_TITLES[random.nextInt(SAMPLE_TITLES.length)];
    }

    private String randomContent() {
        return SAMPLE_CONTENTS[random.nextInt(SAMPLE_CONTENTS.length)];
    }

    private BlogPostStatus randomStatus() {
        BlogPostStatus[] statuses = BlogPostStatus.values();
        return statuses[random.nextInt(statuses.length)];
    }
}
