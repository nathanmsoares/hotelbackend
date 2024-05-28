package br.com.nathan.hotel.core.dto;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class RoomReservationDTO {

    private Long id;

    @NotNull
    private RoomDTO room;

    @NotNull
    private ReservationDTO reservation;

    private Double expense;

    private Boolean closed;

    private Instant createdTime;

}
