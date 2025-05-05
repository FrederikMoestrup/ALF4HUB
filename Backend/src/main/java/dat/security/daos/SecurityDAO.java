package dat.security.daos;


import dat.security.entities.Role;
import dat.entities.User;
import dat.security.exceptions.ApiException;
import dat.security.exceptions.ValidationException;
import dat.dtos.UserDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public class SecurityDAO implements ISecurityDAO {

    private static ISecurityDAO instance;
    private static EntityManagerFactory emf;

    public SecurityDAO(EntityManagerFactory _emf) {
        this.emf = _emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    @Override
    public UserDTO getVerifiedUser(String username, String password) throws ValidationException {
        try (EntityManager em = getEntityManager()) {
            User user = em.find(User.class, username);
            if (user == null)
                throw new EntityNotFoundException("No user found with username: " + username); //RuntimeException
            user.getRoles().size(); // force roles to be fetched from db
            if (!user.verifyPassword(password))
                throw new ValidationException("Wrong password");
            return new UserDTO(user.getUsername(), user.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()));
        }
    }


    @Override
    public User createUser(String username, String password, String email) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            List<User> result = query.getResultList();
            if (!result.isEmpty()) {
                throw new EntityExistsException("User with username: " + username + " already exists");
            }

            User userEntity = new User(username, password);
            userEntity.setEmail(email);
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

            if(username.equals("admin")){
                addRole(new UserDTO(username, password), "admin");
            }

            return userEntity;
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public User addRole(UserDTO userDTO, String newRole) {
        try (EntityManager em = getEntityManager()) {
            User user = em.find(User.class, userDTO.getUsername());
            if (user == null)
                throw new EntityNotFoundException("No user found with username: " + userDTO.getUsername());
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
        }
    }
}

