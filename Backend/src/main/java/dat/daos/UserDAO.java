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

    //Remember SecurityDAO has methods for creating and verifying users and do we really need to delete users?
    //So we are not implementing IDAO here. But we can use this DAO for other methods.

}
