package com.quizzical.userservice.services.postgres;

import com.quizzical.userservice.entities.Player;
import com.quizzical.userservice.exceptions.UserNotFoundException;
import com.quizzical.userservice.repositories.PlayerRepository;
import com.quizzical.userservice.services.PlayerService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.domain.PageRequest;
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
    public Set<Player> getPlayersByUsernames(Set<String> usernames) {
        return playerRepository.findAllByUsernameList(usernames);
    }

    @Override
    public Player getUser(Long id) {
        return playerRepository.findById(id).orElseThrow(UserNotFoundException::new);
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
        playerRepository.save(user);
    }

    @Override
    public Player updateUser(Long id, Player user) {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteUser(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public List<Player> addUsers(List<Player> users) {
        return playerRepository.saveAll(users);
    }

    @Override
    public void deleteUsers(List<Long> ids) {
        playerRepository.deleteAllById(ids);
    }


    @Override
    public void fluctuateMMR(Long id, Integer amount) {
        Player p = playerRepository.findById(id).orElseThrow(UserNotFoundException::new);
        p.changeMMRBy(amount);
        playerRepository.save(p);
    }

    @Override
    public void fluctuateMMRBatch(List<Long> ids, List<Integer> amounts) {
        throw new NotYetImplementedException();
    }

    @Override
    public void banUser(Long id) {
        Player p = playerRepository.findById(id).orElseThrow(UserNotFoundException::new);
        p.setIsBanned(true);
        playerRepository.save(p);
    }
}
