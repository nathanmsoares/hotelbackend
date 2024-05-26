package br.com.nathan.hotel.core.exception;

public class CheckInNotAllowedException extends RuntimeException {

    public CheckInNotAllowedException(String message) {
        super(message);
    }

}
