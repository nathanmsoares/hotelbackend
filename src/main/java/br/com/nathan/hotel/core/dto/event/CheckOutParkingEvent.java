package br.com.nathan.hotel.core.dto.event;

import br.com.nathan.hotel.core.entity.Parking;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CheckOutParkingEvent extends ApplicationEvent {

    private final Long parkingId;

    public CheckOutParkingEvent(Object source) {
        super(source);
        parkingId = (Long) source;
    }
}
