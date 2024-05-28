package br.com.nathan.hotel.core.dto;

import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.entity.RoomReservation;
import jakarta.validation.constraints.NotNull;
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

    private List<ParkingDTO> parkingList;

    private List<RoomReservationDTO> roomReservationList;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private Double totalCost;

    private Integer checkOutHour;

    private Integer checkInHour;

    private Instant createdTime;

}
