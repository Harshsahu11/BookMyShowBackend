package com.bms.BookMyShow.service;

import com.bms.BookMyShow.dto.*;
import com.bms.BookMyShow.exception.ResourceNotFoundException;
import com.bms.BookMyShow.exception.SeatUnavailableException;
import com.bms.BookMyShow.model.*;
import com.bms.BookMyShow.repository.BookingRepository;
import com.bms.BookMyShow.repository.ShowRepository;
import com.bms.BookMyShow.repository.ShowSeatRepository;
import com.bms.BookMyShow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public BookingDto createBooking(BookingRequestDto bookingRequest){

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("User Not Found"));

        Show show = showRepository.findById(bookingRequest.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("Show Not Found"));

        List<ShowSeat> selectedSeats = showSeatRepository.findAllById(bookingRequest.getSeatIds());

        for(ShowSeat seat : selectedSeats ){
            if(!"AVAILABLE".equals(seat.getSeatStatus())){
                throw new SeatUnavailableException("Seat "+ seat.getSeat().getSeatNumber() +"is not available");
            }
            seat.setSeatStatus("LOCKED");
        }

        showSeatRepository.saveAll(selectedSeats);

        Double totalAmount = selectedSeats.stream()
                .mapToDouble(ShowSeat::getPrice)
                .sum();

        //Payment
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentMethod(bookingRequest.getPaymentMethod());
        payment.setStatus("Success");
        payment.setTransactionId(UUID.randomUUID().toString());


        //Booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        booking.setTotalAmount(totalAmount);
        booking.setBookingNumber(UUID.randomUUID().toString());
        booking.setPayment(payment);

        Booking saveBooking = bookingRepository.save(booking);

        selectedSeats.forEach(seat->{
            seat.setSeatStatus("BOOKED");
            seat.setBooking(saveBooking);
        });

        showSeatRepository.saveAll(selectedSeats);
        return mapToBookingDto(saveBooking,selectedSeats);

    }

    private BookingDto mapToBookingDto(Booking booking,List<ShowSeat> seats){

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingNumber(booking.getBookingNumber());
        bookingDto.setBookingTime(booking.getBookingTime());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setTotalAmount(booking.getTotalAmount());

         //user

        UserDto userDto = new UserDto();
        userDto.setId(booking.getUser().getId());
        userDto.setName(booking.getUser().getName());
        userDto.setEmail(booking.getUser().getEmail());
        userDto.setPhoneNumber(booking.getUser().getPhoneNumber());

        bookingDto.setUser(userDto);

        ShowDto showDto = new ShowDto();
        showDto.setId(booking.getShow().getId());
        showDto.setStartTime(booking.getShow().getStartTime());
        showDto.setEndTime(booking.getShow().getEndTime());

        MovieDto movieDto = new MovieDto();
        movieDto.setId(booking.getShow().getMovie().getId());
        movieDto.setTitle(booking.getShow().getMovie().getTitle());
        movieDto.setDescription(booking.getShow().getMovie().getDescription());
        movieDto.setGenre(booking.getShow().getMovie().getGenre());
        movieDto.setLanguage(booking.getShow().getMovie().getLanguage());
        movieDto.setDurationMins(Integer.valueOf(booking.getShow().getMovie().getDurationMins()));
        movieDto.setReleaseDate(booking.getShow().getMovie().getReleaseDate());
        movieDto.setPosterUrl(booking.getShow().getMovie().getPosterUrl());

        showDto.setMovie(movieDto);


        ScreenDto screenDto = new ScreenDto();
        screenDto.setId(booking.getShow().getScreen().getId());
        screenDto.setName(booking.getShow().getScreen().getName());
        screenDto.setTotalSeats(booking.getShow().getScreen().getTotalSeats());

        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setId(booking.getShow().getScreen().getTheater().getId());
        theaterDto.setName(booking.getShow().getScreen().getTheater().getName());
        theaterDto.setAddress(booking.getShow().getScreen().getTheater().getAddress());
        theaterDto.setCity(booking.getShow().getScreen().getTheater().getCity());
        theaterDto.setTotalScreens(booking.getShow().getScreen().getTheater().getTotalScreen());

        screenDto.setTheater(theaterDto);
        showDto.setScreen(screenDto);
        bookingDto.setShow(showDto);

        List<ShowSeatDto> seatDtos = seats.stream()
                .map(seat->{
                    ShowSeatDto seatDto = new ShowSeatDto();
                    seatDto.setId(seat.getId());
                    seatDto.setStatus(seat.getSeatStatus());
                    seatDto.setPrice(seat.getPrice());

                    SeatDto baseSeatDto = new SeatDto();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setBasePrice(seat.getSeat().getBasePrice());

                    seatDto.setSeat(baseSeatDto);
                    return seatDto;
                })
                .collect(Collectors.toList());
        bookingDto.setSeats(seatDtos);


        if(booking.getPayment()!=null){
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(booking.getPayment().getId());
            paymentDto.setAmount(booking.getPayment().getAmount());
            paymentDto.setPaymentMethod(booking.getPayment().getPaymentMethod());
            paymentDto.setPaymentTime(booking.getPayment().getPaymentTime());
            paymentDto.setStatus(booking.getPayment().getStatus());
            paymentDto.setTransactionId(booking.getPayment().getTransactionId());

            bookingDto.setPayment(paymentDto);
        }

        return bookingDto;

    }

}
