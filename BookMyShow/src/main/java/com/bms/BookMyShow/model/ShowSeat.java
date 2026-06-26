package com.bms.BookMyShow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "show_seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "show_id",nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id",nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private String seatStatus;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "booking_id",nullable = false)
    private Booking booking;
}
