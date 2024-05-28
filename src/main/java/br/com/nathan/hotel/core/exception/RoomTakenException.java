package br.com.nathan.hotel.core.exception;

public class RoomTakenException extends RuntimeException {

    public RoomTakenException(String message) {
        super(message);
    }

}
