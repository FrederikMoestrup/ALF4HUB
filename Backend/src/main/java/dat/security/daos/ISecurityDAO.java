package dat.security.daos;

import dat.entities.User;
import dat.security.exceptions.ValidationException;
import dat.dtos.UserDTO;

public interface ISecurityDAO {

    User createUser(String username, String password, String email) throws ValidationException;

    UserDTO getVerifiedUser(String username, String password) throws ValidationException;

    User addRole(UserDTO user, String newRole);

    // Commented code from dev:
    // User createUser(String username, String password);
    // User addRole(int id, String newRole);
}
