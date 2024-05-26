package br.com.nathan.hotel.inbound.rest;

import br.com.nathan.hotel.core.dto.EntityDTO;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.usecase.CheckInReservationUC;
import br.com.nathan.hotel.core.usecase.CreateReservationUC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservation")
@RestController
public record ReservationController(CreateReservationUC createReservationUC,
                                    CheckInReservationUC checkInReservationUC) {

    @PostMapping
    public ResponseEntity<EntityDTO> createReservation(@RequestBody CreateReservationCommand command) {
        Reservation reservation = createReservationUC.execute(command);
        return new ResponseEntity<>(EntityDTO.of(reservation.getId()), HttpStatus.CREATED);
    }

    @PostMapping(path = "checkIn/{reservationId}")
    public ResponseEntity checkIn(@PathVariable("reservationId") Long reservationId) {
        checkInReservationUC.execute(reservationId);
        return ResponseEntity.ok().build();
    }

}
