package br.com.nathan.hotel.inbound.rest;

import br.com.nathan.hotel.core.dto.EntityDTO;
import br.com.nathan.hotel.core.dto.ReservationDTO;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.usecase.CheckInReservationUC;
import br.com.nathan.hotel.core.usecase.CheckOutReservationUC;
import br.com.nathan.hotel.core.usecase.CreateReservationUC;
import br.com.nathan.hotel.core.usecase.FindReservationUC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservation/")
@RestController
public record ReservationController(CreateReservationUC createReservationUC,
                                    CheckInReservationUC checkInReservationUC,
                                    CheckOutReservationUC checkOutReservationUC,
                                    FindReservationUC findReservationUC) {

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

    @PostMapping(path = "checkOut/{reservationId}")
    public ResponseEntity checkOut(@PathVariable("reservationId") Long reservationId) {
        checkOutReservationUC.execute(reservationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "{reservationId}")
    public ResponseEntity<ReservationDTO> findById(@PathVariable("reservationId") Long reservationId) {
        return new ResponseEntity<>(findReservationUC.findById(reservationId), HttpStatus.OK);
    }

}
