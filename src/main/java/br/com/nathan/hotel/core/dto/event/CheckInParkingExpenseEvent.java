package br.com.nathan.hotel.core.dto.event;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.entity.RoomReservation;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class CheckInParkingExpenseEvent extends ApplicationEvent {

    private List<Parking> parkingList;

    public CheckInParkingExpenseEvent(Object source) {
        super(source);
        parkingList = (List<Parking>) source;
    }
}
