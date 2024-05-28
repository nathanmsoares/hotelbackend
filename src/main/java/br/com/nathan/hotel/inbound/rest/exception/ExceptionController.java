package br.com.nathan.hotel.inbound.rest.exception;


import br.com.nathan.hotel.core.exception.CheckInNotAllowedException;
import br.com.nathan.hotel.core.exception.ReservationNotFoundException;
import br.com.nathan.hotel.core.exception.RoomReservationEmptyException;
import br.com.nathan.hotel.core.exception.RoomTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> reservationNotFoundException(ReservationNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CheckInNotAllowedException.class)
    public ResponseEntity<String> checkInNotAllowedException(CheckInNotAllowedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomReservationEmptyException.class)
    public ResponseEntity<String> checkInNotAllowedException(RoomReservationEmptyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomTakenException.class)
    public ResponseEntity<String> roomTakenException(RoomTakenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
