package dat.entities;

import dat.security.entities.ISecurityUser;
import dat.security.entities.Role;
import jakarta.persistence.*;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedQueries(@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable, ISecurityUser {

    @Serial
    private static final long serialVersionUID = 1L;

    //Attributes
    @Id
    @Basic(optional = false)
    @Column(name = "username", length = 25)
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    //Relations
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_name", referencedColumnName = "username")}, inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<PlayerAccount> playerAccounts;

    //As a host
    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Tournament> tournaments;

    //As a team captain
    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Team> teams;

    public Set<String> getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        Set<String> rolesAsStrings = new HashSet<>();
        roles.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, this.password);
    }


    //Idea: Make a builder for the User class

    public User(String userName, String userPass) {
        this.username = userName;
        this.password = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public User(String userName, Set<Role> roleEntityList) {
        this.username = userName;
        this.roles = roleEntityList;
    }

    public void addRole(Role role) {
        if (role == null) {
            return;
        }
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(String userRole) {
        roles.stream()
                .filter(role -> role.getRoleName().equals(userRole))
                .findFirst()
                .ifPresent(role -> {
                    roles.remove(role);
                    role.getUsers().remove(this);
                });
    }
    public void setPlayerAccounts(List<PlayerAccount> playerAccounts) {
        if(playerAccounts != null) {
            this.playerAccounts = playerAccounts;
            for (PlayerAccount playerAccount : playerAccounts) {
                playerAccount.setUser(this);
            }
        }
    }

    public void addPlayerAccount(PlayerAccount playerAccount) {
        if (playerAccount != null) {
            this.playerAccounts.add(playerAccount);
            playerAccount.setUser(this);
        }
    }

    public void setTournaments(List<Tournament> tournaments) {
        if(tournaments != null) {
            this.tournaments = tournaments;
            for (Tournament tournament : tournaments) {
                tournament.setHost(this);
            }
        }
    }

    public void addTournament(Tournament tournament) {
        if (tournament != null) {
            this.tournaments.add(tournament);
            tournament.setHost(this);
        }
    }

    public void setTeams(List<Team> teams) {
        if(teams != null) {
            this.teams = teams;
            for (Team team : teams) {
                team.setTeamCaptain(this);
            }
        }
    }

    public void addTeam(Team team) {
        if (team != null) {
            this.teams.add(team);
            team.setTeamCaptain(this);
        }
    }

}

