package br.com.nathan.hotel.core.exception;

public class ParkingNotFoundException extends RuntimeException {

    public ParkingNotFoundException(String message) {
        super(message);
    }

}
