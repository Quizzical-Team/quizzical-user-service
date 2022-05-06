package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.entities.RoleType;
import com.quizzical.userservice.exceptions.UserNotFoundException;
import com.quizzical.userservice.repositories.PlayerRepository;
import com.quizzical.userservice.services.PlayerService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class PostgresPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;

    public PostgresPlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Set<Player> searchPlayersStartingWith(String username) {
        return playerRepository.findPlayersByUsernameIsStartingWith(username);
    }

    @Override
    public List<Player> getPlayersByUsernames(Set<String> usernames) {
        return playerRepository.findAllByUsernameSet(usernames);
    }

    @Override
    public Player getUser(String username) {
        return playerRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<Player> getUsersPaginated(Integer pageNo, Integer pageSize) {
        return playerRepository.findAll(PageRequest.of(pageNo, pageSize)).toList();
    }

    @Override
    public List<Player> getAllUsers() {
        return playerRepository.findAll();
    }

    @Override
    public void addUser(Player user) {
        try {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRole(RoleType.ROLE_PLAYER);
            playerRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
}

    @Override
    public Player updateUser(String username, Player user) {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteUser(String username) {
        playerRepository.deleteByUsername(username);
    }

    @Override
    public Set<Player> getTop10ByMmr() {
        return playerRepository.getTopPlayersByMmr();
    }

    @Override
    public Boolean fluctuateMMR(String username, Integer amount) {
        Player p = playerRepository.findByUsername(username).orElse(null);
        if (p == null)
            return false;
        p.changeMMRBy(amount);
        playerRepository.save(p);
        return true;
    }

    @Override
    public Boolean fluctuateMMRBatch(Set<String> usernames, Collection<Integer> amounts) {
        if (usernames.size() != amounts.size())
            return false;
        List<Player> players = playerRepository.findAllByUsernameSet(usernames);
        if (usernames.size() != players.size())
            return false;
        int idx = 0;
        for (Integer amount : amounts) {
            players.get(idx).changeMMRBy(amount);
            idx++;
        }

        playerRepository.saveAll(players);
        return true;
    }

    @Override
    public void banUser(Long id) {
        Player p = playerRepository.findById(id).orElseThrow(UserNotFoundException::new);
        p.setIsBanned(true);
        playerRepository.save(p);
    }
}
