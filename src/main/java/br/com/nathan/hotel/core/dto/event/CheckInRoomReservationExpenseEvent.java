package br.com.nathan.hotel.core.dto.event;

import br.com.nathan.hotel.core.entity.RoomReservation;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class CheckInRoomReservationExpenseEvent extends ApplicationEvent {

    private List<RoomReservation> roomReservationList;

    public CheckInRoomReservationExpenseEvent(Object source) {
        super(source);
        roomReservationList = (List<RoomReservation>) source;
    }
}
