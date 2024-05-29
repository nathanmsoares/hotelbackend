package br.com.nathan.hotel.core.dto.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CheckOutRoomReservationEvent extends ApplicationEvent {

    private final Long roomReservationId;

    public CheckOutRoomReservationEvent(Object source) {
        super(source);
        roomReservationId = (Long) source;
    }
}
