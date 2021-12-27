package com.softserve.bot.service.repository;

import com.softserve.bot.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
