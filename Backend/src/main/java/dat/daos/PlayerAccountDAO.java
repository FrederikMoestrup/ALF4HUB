package dat.daos;

import dat.dtos.PlayerAccountDTO;
import dat.entities.PlayerAccount;
import dat.entities.Team;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PlayerAccountDAO implements IDAO<PlayerAccountDTO, Integer> {

    private static PlayerAccountDAO instance;
    private static EntityManagerFactory emf;

    public static PlayerAccountDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PlayerAccountDAO();
        }
        return instance;
    }

    @Override
    public PlayerAccountDTO getById(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            PlayerAccount playerAccount = em.find(PlayerAccount.class, id);
            if (playerAccount == null) {
                throw new ApiException(404, "PlayerAccount not found");
            }
            return new PlayerAccountDTO(playerAccount);
        }
    }

    @Override
    public List<PlayerAccountDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            List<PlayerAccount> playerAccounts = em.createQuery("SELECT p FROM PlayerAccount p", PlayerAccount.class).getResultList();
            return playerAccounts.stream().map(PlayerAccountDTO::new).toList();
        }
    }

    @Override
    public PlayerAccountDTO create(PlayerAccountDTO playerAccountDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            PlayerAccount playerAccount = new PlayerAccount(playerAccountDTO);
            em.persist(playerAccount);
            em.getTransaction().commit();
            return new PlayerAccountDTO(playerAccount);
        }
    }

    @Override
    public PlayerAccountDTO update(Integer id, PlayerAccountDTO playerAccountDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            PlayerAccount playerAccount = em.find(PlayerAccount.class, id);
            if (playerAccount == null) {
                throw new ApiException(404, "PlayerAccount not found");
            }

            playerAccount.setPlayerAccountName(playerAccountDTO.getPlayerAccountName());
            playerAccount.setGame(playerAccountDTO.getGame());
            playerAccount.setRank(playerAccountDTO.getRank());

            em.getTransaction().commit();
            return new PlayerAccountDTO(playerAccount);
        }
    }

    @Override
    public PlayerAccountDTO delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            PlayerAccount playerAccount = em.find(PlayerAccount.class, id);
            if (playerAccount == null) {
                throw new ApiException(404, "PlayerAccount not found");
            }
            playerAccount.getTeams().size();
            em.remove(playerAccount);
            em.getTransaction().commit();
            return new PlayerAccountDTO(playerAccount);
        }
    }


    // This method is commented out because im not sure if it works
/*
    public static List<PlayerAccountDTO> getPlayersByTeamId(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            Team team = em.find(Team.class, id);
            if (team == null) {
                throw new ApiException(404, "Team not found");
            }
            return team.getTeamAccounts().stream()
                .map(PlayerAccountDTO::new)
                .toList();
        }
    }
*/

//Might work but not sure
    public List<PlayerAccount> getPlayersByTeamId(int teamId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<PlayerAccount> query = em.createQuery(
                    "SELECT p FROM Team t JOIN t.teamAccounts p WHERE t.id = :teamId",
                    PlayerAccount.class
            );
            query.setParameter("teamId", teamId);
            return query.getResultList();
        }
    }


}
