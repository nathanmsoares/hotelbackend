package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.event.CheckOutParkingEvent;
import br.com.nathan.hotel.core.dto.event.CheckOutRoomReservationEvent;
import br.com.nathan.hotel.core.usecase.CheckOutParkingUC;
import br.com.nathan.hotel.core.usecase.CheckOutRoomReservationUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckOutRoomReservationEventListener implements ApplicationListener<CheckOutRoomReservationEvent> {

    private final CheckOutRoomReservationUC checkOutRoomReservationUC;

    @Override
    public void onApplicationEvent(CheckOutRoomReservationEvent event) {
        checkOutRoomReservationUC.execute(event.getRoomReservationId());
    }
}
