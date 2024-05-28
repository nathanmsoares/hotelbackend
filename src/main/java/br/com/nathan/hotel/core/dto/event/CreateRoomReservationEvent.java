package br.com.nathan.hotel.core.dto.event;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateRoomReservationEvent extends ApplicationEvent {

    private final Reservation reservation;

    private final Room room;

    public CreateRoomReservationEvent(Object source, Room room) {
        super(source);
        reservation = (Reservation) source;
        this.room = room;
    }

}
