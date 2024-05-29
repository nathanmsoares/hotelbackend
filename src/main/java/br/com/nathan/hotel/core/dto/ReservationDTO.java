package br.com.nathan.hotel.core.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ReservationDTO {

    private Long id;

    private List<GuestDTO> guestList;

    private ParkingDTO parking;

    private RoomReservationDTO roomReservation;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private Double totalCost;

    private Integer checkOutHour;

    private Integer checkInHour;

    private Instant createdTime;

}
