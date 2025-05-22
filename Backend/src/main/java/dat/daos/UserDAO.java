package dat.daos;

import dat.dtos.UserDTO;
import dat.entities.User;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class UserDAO implements IDAO<UserDTO, Integer> {

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


    public void updateProfilePicture(int userId, String url) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);

            if (user == null) {
                throw new EntityNotFoundException("User with ID " + userId + " not found");
            }

            user.setProfilePicture(url);
            em.merge(user); // Use merge to ensure entity is updated
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error updating profile picture: " + e.getMessage(), e);
        }
    }


    public String getProfilePictureById(int userId) {
        try (EntityManager em = emf.createEntityManager()) {
            User foundUser = em.find(User.class, userId);
            if (foundUser == null) {
                throw new EntityNotFoundException("User with ID " + userId + " not found");
            }
            return foundUser.getProfilePicture();
        }
    }

}
