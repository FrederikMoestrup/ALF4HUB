package dat.utils;

import dat.daos.BlogPostDAO;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BlogPostCleanupScheduler {

    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    // Set your cleanup interval here (e.g., once every 24 hours)
    private static final long INTERVAL_HOURS = 24;

    private final EntityManagerFactory emf;

    // Singleton instance
    private static BlogPostCleanupScheduler instance;

    private BlogPostCleanupScheduler(EntityManagerFactory emf) {
        this.emf = emf;
        scheduleTask();
    }

    public static void initialize(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new BlogPostCleanupScheduler(emf);
        }
    }

    private void scheduleTask() {
        scheduler.scheduleAtFixedRate(BlogPostDAO::deleteOldDrafts, 0, INTERVAL_HOURS, TimeUnit.HOURS);
    }
}
