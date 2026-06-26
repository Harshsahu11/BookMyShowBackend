package com.bms.BookMyShow.repository;

import com.bms.BookMyShow.model.Payment;
import com.bms.BookMyShow.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen,Long> {

    List<Screen> findBytheaterId(Long theaterId);


}
