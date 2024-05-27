package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public class CreateParkingCommand {

    private Reservation reservation;

    public Parking toEntity() {
        log.info("Creating Parking on reservation id {}", reservation.getId());
        Parking parking = Parking.builder()
                .reservation(reservation)
                .build();
        return parking;
    }

}
