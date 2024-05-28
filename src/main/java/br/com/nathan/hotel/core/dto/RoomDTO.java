package br.com.nathan.hotel.core.dto;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class RoomDTO {

    private Long id;

    @Positive
    private Integer floor;

    @Positive
    private Integer number;

    private RoomReservationDTO roomReservation;

    private Boolean taken;

    private Instant createdTime;

}
