package dat.daos;

import dat.dtos.PlayerAccountDTO;
import dat.entities.PlayerAccount;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
    public List<PlayerAccountDTO> getAll(){
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
}
