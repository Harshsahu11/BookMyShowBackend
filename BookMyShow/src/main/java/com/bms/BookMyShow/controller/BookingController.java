package com.bms.BookMyShow.controller;

import com.bms.BookMyShow.dto.BookingDto;
import com.sun.net.httpserver.HttpsConfigurator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @GetMapping
     public ResponseEntity<BookingDto> createBooking(
             @Valid
             @RequestBody BookingDto bookingRequest){

         return new ResponseEntity<>(bookingservice.createBooking(bookingRequest), HttpStatus.CREATED);


     }
}
