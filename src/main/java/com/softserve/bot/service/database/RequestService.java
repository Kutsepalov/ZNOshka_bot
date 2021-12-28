package com.softserve.bot.service.database;

import com.softserve.bot.model.entity.Request;
import com.softserve.bot.model.entity.User;
import com.softserve.bot.service.repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<Request> list() {
        return requestRepository.findAll();
    }

    @Transactional
    public Request save(long chatID, long subjectSet) {
        User user = userService.save(chatID);
        Request request = new Request();
        request.setUser(user);
        request.setDatetime(Timestamp.from(Instant.now()));
        request.setSetSubjects(subjectSet);
        request = requestRepository.save(request);
        return request;
    }

    @Transactional
    public Request save(User x, long subjectSet) {
        return save(x.getId(), subjectSet);
    }
}