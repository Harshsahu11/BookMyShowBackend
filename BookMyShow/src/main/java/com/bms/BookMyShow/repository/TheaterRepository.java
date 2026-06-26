package com.bms.BookMyShow.repository;

import com.bms.BookMyShow.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater,Long> {

    List<Theater> findByShowId(String city);


}
