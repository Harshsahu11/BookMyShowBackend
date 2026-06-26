package com.bms.BookMyShow.controller;

import com.bms.BookMyShow.dto.BookingDto;
import com.bms.BookMyShow.dto.BookingRequestDto;
import com.bms.BookMyShow.service.BookingService;
import com.sun.net.httpserver.HttpsConfigurator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
     public ResponseEntity<BookingDto> createBooking(
            @Valid
             @RequestBody BookingRequestDto bookingRequest){

         return new ResponseEntity<>(bookingService.createBooking(bookingRequest), HttpStatus.CREATED);

     }

     @GetMapping("/{id}")
     public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id){
        return new ResponseEntity<>(bookingService.getBookingById(id),HttpStatus.OK);
     }



}
