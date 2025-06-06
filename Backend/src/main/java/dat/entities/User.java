package dat.entities;

import dat.dtos.UserDTO;
import dat.security.entities.ISecurityUser;
import dat.security.entities.Role;
import jakarta.persistence.*;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NamedQueries(@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User implements Serializable, ISecurityUser {

    @Serial
    private static final long serialVersionUID = 1L;

    //Attributes
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", length = 25, nullable = false, unique = true)
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "strikes")
    private int strikes = 0;

    @Column(name = "profile_picture")
    private String profilePicture;


    //Relations
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<PlayerAccount> playerAccounts = new ArrayList<>();

    //As a host
    @OneToMany(mappedBy = "host", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Tournament> tournaments = new ArrayList<>();

    //As a team captain
    @OneToMany(mappedBy = "teamCaptain", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "tournamentTeamCaptain", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<TournamentTeam> tournamentTeams = new ArrayList<>();

    //One user can have multiple blogposts, but a blogpost belongs to one user
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<BlogPost> blogPosts = new ArrayList<>();

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

    public User(String username, String password, String email) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
    }

    public User(UserDTO dto) {
        this.id = dto.getId();
        this.username = dto.getUsername();
        this.email = dto.getEmail();
        if (dto.getRoles() != null) {
            this.roles = dto.getRoles().stream()
                    .map(roleName -> new Role(roleName))
                    .collect(Collectors.toSet());
        }
        if (dto.getPlayerAccounts() != null) {
            setPlayerAccounts(dto.getPlayerAccounts().stream()
                    .map(PlayerAccount::new)
                    .collect(Collectors.toList()));
        }
        if (dto.getTournaments() != null) {
            setTournaments(dto.getTournaments().stream()
                    .map(Tournament::new)
                    .collect(Collectors.toList()));
        }
        if (dto.getTeams() != null) {
            setTeams(dto.getTeams().stream()
                    .map(Team::new)
                    .collect(Collectors.toList()));
        }
        if (dto.getTournamentTeams() != null) {
            setTournamentTeams(dto.getTournamentTeams().stream()
                    .map(TournamentTeam::new)
                    .collect(Collectors.toList()));
        }
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
        if (playerAccounts != null) {
            this.playerAccounts = playerAccounts;
            for (PlayerAccount playerAccount : playerAccounts) {
                playerAccount.setUser(this);
            }
        }
    }

    public void addPlayerAccount(PlayerAccount playerAccount) {
        if (playerAccount != null && !playerAccounts.contains(playerAccount)) {
            this.playerAccounts.add(playerAccount);
            playerAccount.setUser(this);
        }
    }

    public void setTournaments(List<Tournament> tournaments) {
        if (tournaments != null) {
            this.tournaments = tournaments;
            for (Tournament tournament : tournaments) {
                tournament.setHost(this);
            }
        }
    }

    public void addTournament(Tournament tournament) {
        if (tournament != null && !tournaments.contains(tournament)) {
            this.tournaments.add(tournament);
            tournament.setHost(this);
        }
    }

    public void setTeams(List<Team> teams) {
        if (teams != null) {
            this.teams = teams;
            for (Team team : teams) {
                team.setTeamCaptain(this);
            }
        }
    }

    public void addTeam(Team team) {
        if (team != null && !teams.contains(team)) {
            this.teams.add(team);
            team.setTeamCaptain(this);
        }
    }

    public void addStrike() {
        this.strikes++;
    }

    public void removeTeam(Team team) {
        if (team == null || !teams.contains(team)) {
            return;
        }
        teams.remove(team);
    }

    public void setTournamentTeams(List<TournamentTeam> tournamentTeams) {
        if (tournamentTeams != null) {
            this.tournamentTeams = tournamentTeams;
            for (TournamentTeam tournamentTeam : tournamentTeams) {
                tournamentTeam.setTournamentTeamCaptain(this);
            }
        }
    }

    public void addTournamentTeam(TournamentTeam tournamentTeam) {
        if (tournamentTeam != null && !tournamentTeams.contains(tournamentTeam)) {
            this.tournamentTeams.add(tournamentTeam);
            tournamentTeam.setTournamentTeamCaptain(this);
        }
    }

    public void removeTournamentTeam(TournamentTeam tournamentTeam) {
        if (tournamentTeam == null || !tournamentTeams.contains(tournamentTeam)) {
            return;
        }
        tournamentTeams.remove(tournamentTeam);
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        if (blogPosts != null) {
            this.blogPosts = blogPosts;
            for (BlogPost blogPost : blogPosts) {
                blogPost.setUser(this);
            }
        }
    }

    public void addBlogPost(BlogPost blogPost) {
        if (blogPost != null && !blogPosts.contains(blogPost)) {
            this.blogPosts.add(blogPost);
            blogPost.setUser(this);
        }
    }


}

