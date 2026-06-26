package com.bms.BookMyShow.controller;

import com.bms.BookMyShow.dto.BookingDto;
import com.bms.BookMyShow.dto.BookingRequestDto;
import com.bms.BookMyShow.dto.MovieDto;
import com.bms.BookMyShow.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(
            @Valid
            @RequestBody MovieDto movieDto){
        return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id){
        return new ResponseEntity<>(movieService.getMovieById(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovie(){
        return new ResponseEntity<>(movieService.getAllMovies(),HttpStatus.OK);
    }


}
