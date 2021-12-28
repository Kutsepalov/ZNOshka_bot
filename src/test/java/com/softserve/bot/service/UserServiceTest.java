package com.softserve.bot.service;

import com.softserve.bot.model.entity.User;
import com.softserve.bot.service.database.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        List<User> users = service.list();
        assertEquals(users.size(), 1);
    }
}