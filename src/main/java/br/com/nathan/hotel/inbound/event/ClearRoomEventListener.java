package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.event.CheckOutRoomReservationEvent;
import br.com.nathan.hotel.core.dto.event.ClearRoomEvent;
import br.com.nathan.hotel.core.usecase.CheckOutRoomReservationUC;
import br.com.nathan.hotel.core.usecase.ClearRoomUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClearRoomEventListener implements ApplicationListener<ClearRoomEvent> {

    private final ClearRoomUC clearRoomUC;

    @Override
    public void onApplicationEvent(ClearRoomEvent event) {
        clearRoomUC.execute(event.getRoomId());
    }
}
