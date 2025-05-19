package dat.daos;

import dat.dtos.UserDTO;
import dat.entities.User;
import dat.exceptions.ApiException;
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

    @Override
    public UserDTO getById(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            User user = em.find(User.class, id);
            if (user == null) {
                throw new ApiException(404, "User not found");
            }
            return new UserDTO(user);
        }
    }

    public UserDTO getByUsername(String username) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            if (user == null) {
                throw new ApiException(404, "User not found");
            }
            return new UserDTO(user);
        }
    }

    public User getUserByUsername(String username) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            if (user == null) {
                throw new ApiException(404, "User not found");
            }
            return user;
        }
    }

    public UserDTO addStrike(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user == null) {
                throw new ApiException(404, "User not found");
            }
            user.addStrike();
            em.merge(user);
            em.getTransaction().commit();
            return new UserDTO(user);
        }
    }


    //Husk SecurityDAO har create. Pas på med delete og update. Måske skal getAll bruges?
    @Override
    public List<UserDTO> getAll() {
        return List.of();
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO update(Integer integer, UserDTO userDTO) throws ApiException {
        return null;
    }

    @Override
    public UserDTO delete(Integer integer) throws ApiException {
        return null;
    }


}
