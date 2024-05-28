package br.com.nathan.hotel.core.dto.event;

import br.com.nathan.hotel.core.entity.Reservation;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateParkingEvent extends ApplicationEvent {

    private final Reservation reservation;

    private final Boolean parkingRequested;

    public CreateParkingEvent(Object source, Boolean parkingRequested) {
        super(source);
        this.reservation = (Reservation) source;
        this.parkingRequested = parkingRequested;
    }
}
