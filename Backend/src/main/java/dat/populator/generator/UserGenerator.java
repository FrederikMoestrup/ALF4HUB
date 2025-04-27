package dat.populator.generator;

import dat.entities.User;
import dat.security.entities.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates users for testing, supporting configurable role combinations and usernames.
 */
public class UserGenerator implements TestDataGenerator<User> {

    private final List<UserType> userTypes;
    private final String password;

    /**
     * @param userTypes List of user types to generate.
     * @param password  Password to use for all users (for simplicity).
     */
    public UserGenerator(List<UserType> userTypes, String password) {
        this.userTypes = userTypes;
        this.password = password;
    }

    @Override
    public List<User> generate() {
        List<User> users = new ArrayList<>();
        int globalIndex = 1;

        for (UserType type : userTypes) {
            for (int i = 0; i < type.count; i++) {
                String username;

                if (type.explicitNames != null && i < type.explicitNames.size()) {
                    username = type.explicitNames.get(i);
                } else {
                    username = type.namePrefix + globalIndex;
                }

                User user = new User(username, password);
                type.roles.forEach(user::addRole);
                users.add(user);
                globalIndex++;
            }
        }

        return users;
    }

    /**
     * Represents a user type: a role combination and how many of them to create.
     */
    public static class UserType {
        public final String namePrefix; // e.g. "Admin", "User"
        public final List<Role> roles;
        public final int count;
        public final List<String> explicitNames; // Optional: use these usernames if provided

        public UserType(String namePrefix, List<Role> roles, int count) {
            this(namePrefix, roles, count, null);
        }

        public UserType(String namePrefix, List<Role> roles, int count, List<String> explicitNames) {
            this.namePrefix = namePrefix;
            this.roles = roles;
            this.count = count;
            this.explicitNames = explicitNames;
        }
    }
}
