package com.bms.BookMyShow.service;

import com.bms.BookMyShow.dto.*;
import com.bms.BookMyShow.exception.ResourceNotFoundException;
import com.bms.BookMyShow.model.Movie;
import com.bms.BookMyShow.model.Screen;
import com.bms.BookMyShow.model.Show;
import com.bms.BookMyShow.model.ShowSeat;
import com.bms.BookMyShow.repository.MovieRepository;
import com.bms.BookMyShow.repository.ScreenRepository;
import com.bms.BookMyShow.repository.ShowRepository;
import com.bms.BookMyShow.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public ShowDto createShow(ShowDto showDto){

        Show show = new Show();

        Movie movie = movieRepository.findById(showDto.getMovie().getId())
                .orElseThrow(()->new ResourceNotFoundException("Movie Not Found"));

        Screen screen = screenRepository.findById(showDto.getScreen().getId())
                .orElseThrow(()->new ResourceNotFoundException("Screen Not Found"));

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(showDto.getStartTime());
        show.setEndTime(showDto.getEndTime());

        Show savedShow = showRepository.save(show);

        List<ShowSeat> availableSeats = showSeatRepository.findByShowIdAndStatus(
                savedShow.getId(), "AVAILABLE"
        );
        return mapToDto(savedShow,availableSeats);
    }

    public ShowDto getShowById(Long id){
        Show show = showRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Show Not Found with id : "+id));

        List<ShowSeat> availableSeats = showSeatRepository.findByShowIdAndStatus(
                show.getId(), "AVAILABLE"
        );

        return mapToDto(show,availableSeats);
    }

    public List<ShowDto> getAllShows(){
        List<Show> shows = showRepository.findAll();
        return shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeats = showSeatRepository.findByShowIdAndStatus(
                            show.getId(), "AVAILABLE"
                    );
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowsByMovie(Long movieId){
        List<Show> shows = showRepository.findByMovieId(movieId);
        return shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeats = showSeatRepository.findByShowIdAndStatus(
                            show.getId(), "AVAILABLE"
                    );
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowsByMovieAndCity(Long movieId,String city){
        List<Show> shows = showRepository.findByMovie_IdAndScreen_Theater_City(movieId,city);
        return shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeats = showSeatRepository.findByShowIdAndStatus(
                            show.getId(), "AVAILABLE"
                    );
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    private ShowDto mapToDto(Show show,List<ShowSeat> availableSeat){
        ShowDto showDto = new ShowDto();
        showDto.setId(show.getId());
        showDto.setStartTime(show.getStartTime());
        showDto.setEndTime(show.getEndTime());

        showDto.setMovie(new MovieDto(
                show.getMovie().getId(),
                show.getMovie().getTitle(),
                show.getMovie().getDescription(),
                show.getMovie().getLanguage(),
                show.getMovie().getGenre(),
                show.getMovie().getDurationMins(),
                show.getMovie().getReleaseDate(),
                show.getMovie().getPosterUrl()
        ));

        TheaterDto theaterDto = new TheaterDto(
                show.getScreen().getTheater().getId(),
                show.getScreen().getTheater().getName(),
                show.getScreen().getTheater().getAddress(),
                show.getScreen().getTheater().getCity(),
                show.getScreen().getTheater().getTotalScreen()
        );


        showDto.setScreen(new ScreenDto(
                show.getScreen().getId(),
                show.getScreen().getName(),
                show.getScreen().getTotalSeats(),
                theaterDto
        ));

        List<ShowSeatDto> seatDtos = availableSeat.stream()
                .map(seat->{
                    ShowSeatDto seatDto = new ShowSeatDto();
                    seatDto.setId(seat.getId());
                    seatDto.setStatus(seat.getSeatStatus());
                    seatDto.setPrice(seat.getPrice());

                    SeatDto baseSeatDto = new SeatDto();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatDto.setBasePrice(seat.getSeat().getBasePrice());

                    seatDto.setSeat(baseSeatDto);

                    return seatDto;
                })
                .collect(Collectors.toList());


        showDto.setAvailableSeats(seatDtos);
        return showDto;
    }
}
