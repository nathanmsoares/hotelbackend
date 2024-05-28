package br.com.nathan.hotel.core.dto.event;

import br.com.nathan.hotel.core.entity.Parking;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CheckInParkingExpenseEvent extends ApplicationEvent {

    private Parking parking;

    public CheckInParkingExpenseEvent(Object source) {
        super(source);
        parking = (Parking) source;
    }
}
