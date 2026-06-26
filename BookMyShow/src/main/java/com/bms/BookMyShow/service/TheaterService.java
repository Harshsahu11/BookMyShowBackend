package com.bms.BookMyShow.service;

import com.bms.BookMyShow.dto.TheaterDto;
import com.bms.BookMyShow.exception.ResourceNotFoundException;
import com.bms.BookMyShow.model.Theater;
import com.bms.BookMyShow.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public TheaterDto createTheater(TheaterDto theaterDto){
        Theater theater = mapToEntity(theaterDto);
        Theater savedTheater = theaterRepository.save(theater);
        return mapToDto(savedTheater);
    }

    private TheaterDto mapToDto(Theater theater) {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setId(theater.getId());
        theaterDto.setName(theater.getName());
        theaterDto.setAddress(theater.getAddress());
        theaterDto.setCity(theater.getCity());
        theaterDto.setTotalScreens(theater.getTotalScreen());
        return theaterDto;
    }

    public TheaterDto getTheaterById(Long id){
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Theater Not Found with Id : "+id));

        return mapToDto(theater);
    }

    private List<TheaterDto> getAllTheaters(){
        List<Theater> theaters = theaterRepository.findAll();
        return theaters.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TheaterDto updateTheater(Long id, TheaterDto theaterDto) {

        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater Not Found with Id : " + id));

        theater.setName(theaterDto.getName());
        theater.setAddress(theaterDto.getAddress());
        theater.setCity(theaterDto.getCity());
        theater.setTotalScreen(theaterDto.getTotalScreens());

        Theater updatedTheater = theaterRepository.save(theater);
        return mapToDto(updatedTheater);
    }

    public void deleteTheater(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater Not Found with Id : " + id));
        theaterRepository.delete(theater);
    }

    private List<TheaterDto> getAllTheatersByCity(String city){
        List<Theater> theaters = theaterRepository.findByCity(city);
        return theaters.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private Theater mapToEntity(TheaterDto theaterDto) {
        Theater theater = new Theater();
        theater.setName(theaterDto.getName());
        theater.setAddress(theaterDto.getAddress());
        theater.setCity(theaterDto.getCity());
        theater.setTotalScreen(theaterDto.getTotalScreens());
        return theater;
    }
}
