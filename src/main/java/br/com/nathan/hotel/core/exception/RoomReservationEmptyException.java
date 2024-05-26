package br.com.nathan.hotel.core.exception;

public class RoomReservationEmptyException extends RuntimeException {

    public RoomReservationEmptyException(String message) {
        super(message);
    }

}
