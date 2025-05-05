package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populate;
import jakarta.persistence.EntityManagerFactory;

import static dat.config.Populate.clearDatabase;
import static dat.config.Populate.populateDatabase;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
        ApplicationConfig.startServer(7070);
        clearDatabase(emf);
        populateDatabase(emf);
    }
}