package br.com.nathan.hotel.core.dto.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CheckOutReservationEvent extends ApplicationEvent {

    private final Long reservationId;

    public CheckOutReservationEvent(Object source) {
        super(source);
        this.reservationId = (Long) source;
    }
}
