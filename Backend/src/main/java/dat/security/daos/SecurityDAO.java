package dat.security.daos;


import dat.dtos.UserDTO;
import dat.entities.User;
import dat.security.entities.Role;
import dat.security.exceptions.ApiException;
import dat.security.exceptions.ValidationException;
import jakarta.persistence.*;

import java.util.stream.Collectors;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public class SecurityDAO implements ISecurityDAO {

    private static ISecurityDAO instance;
    private final EntityManagerFactory emf;

    public SecurityDAO(EntityManagerFactory _emf) {
        this.emf = _emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public UserDTO getVerifiedUser(String username, String password) throws ValidationException {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);

            User user = query.getSingleResult();

            user.getRoles().size(); // force roles to be fetched from db
            if (!user.verifyPassword(password))
                throw new ValidationException("Wrong password");

            return new UserDTO(user.getUsername(), user.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()));
        } catch (NoResultException e) {
            throw new EntityNotFoundException("No user found with username: " + username); //RuntimeException
        }
    }

    @Override
    public User createUser(String username, String password) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);

            if (!query.getResultList().isEmpty()) {
                throw new EntityExistsException("User with username '" + username + "' already exists");
            }

            User userEntity = new User(username, password);
            em.getTransaction().begin();
            Role userRole = em.find(Role.class, "user");

            // Check if user role exists, if not create it
            if (userRole == null) {
                userRole = new Role("user");
                em.persist(userRole);
            }
            userEntity.addRole(userRole);

            em.persist(userEntity);
            em.getTransaction().commit();

            if (username.equals("admin")) {
                addRole(new UserDTO(username, password), "admin");
            }

            return userEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public User addRole(UserDTO userDTO, String newRole) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", userDTO.getUsername());

            User user = query.getSingleResult();

            em.getTransaction().begin();
            Role role = em.find(Role.class, newRole);
            if (role == null) {
                role = new Role(newRole);
                em.persist(role);
            }

            user.addRole(role);
            //em.merge(user);
            em.getTransaction().commit();
            return user;
        } catch (NoResultException e) {
            throw new EntityNotFoundException("No user found with username: " + userDTO.getUsername()); //RuntimeException
        }
    }
}
