package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.PopulateTeamB;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
        ApplicationConfig.startServer(7070);

        //PopulateTeamB.populateDatabase(emf);
    }
}