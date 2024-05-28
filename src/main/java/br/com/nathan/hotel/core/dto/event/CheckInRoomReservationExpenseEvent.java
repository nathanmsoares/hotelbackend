package br.com.nathan.hotel.core.dto.event;

import br.com.nathan.hotel.core.entity.RoomReservation;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CheckInRoomReservationExpenseEvent extends ApplicationEvent {

    private final RoomReservation roomReservation;

    public CheckInRoomReservationExpenseEvent(Object source) {
        super(source);
        roomReservation = (RoomReservation) source;
    }
}
