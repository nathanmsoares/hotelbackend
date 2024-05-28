package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.event.CheckInRoomReservationExpenseEvent;
import br.com.nathan.hotel.core.usecase.SetRoomReservationExpenseUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckInRoomReservationExpenseEventListener implements ApplicationListener<CheckInRoomReservationExpenseEvent> {

    private final SetRoomReservationExpenseUC setRoomReservationExpenseUC;

    @Override
    public void onApplicationEvent(CheckInRoomReservationExpenseEvent event) {
        setRoomReservationExpenseUC.execute(event.getRoomReservation());
    }
}
