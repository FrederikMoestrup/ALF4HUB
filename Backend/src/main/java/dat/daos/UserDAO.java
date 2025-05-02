package dat.daos;

import dat.dtos.UserDTO;
import dat.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDAO{

    private static UserDAO instance;
    private static EntityManagerFactory emf;

    public static UserDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }

    public User findById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(User.class, id);
        }
    }

    public User update(User user) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User updatedUser = em.merge(user);
            em.getTransaction().commit();
            return updatedUser;
        }
    }


    //Remember SecurityDAO has methods for creating and verifying users and do we really need to delete users?
    //So we are not implementing IDAO here. But we can use this DAO for other methods.

}
