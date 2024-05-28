package br.com.nathan.hotel.core.dto;

import br.com.nathan.hotel.core.entity.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class ParkingDTO {

    private Long id;

    private Reservation reservation;

    private Double expense;

    private Boolean closed;

    private Instant createdTime;

}
