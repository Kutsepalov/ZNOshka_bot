package com.softserve.bot.service.database;

import com.softserve.bot.model.entity.User;
import com.softserve.bot.service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repo;

    @Transactional(readOnly = true)
    public List<User> list() {
        return repo.findAll();
    }

    @Transactional
    public User save(long chatID) {
        return save(new User(chatID, Timestamp.from(Instant.now()), null));
    }

    @Transactional
    public User save(User x) {
        repo.save(x);
        return x;
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return repo.findById(id);
    }
}
