package com.bms.BookMyShow.repository;

import com.bms.BookMyShow.model.Theater;
import com.bms.BookMyShow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Boolean existingByEmail(String Email);
}
