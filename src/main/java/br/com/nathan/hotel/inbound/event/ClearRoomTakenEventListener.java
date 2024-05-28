package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.event.ClearRoomTaken;
import br.com.nathan.hotel.core.dto.event.SetRoomTaken;
import br.com.nathan.hotel.core.usecase.ClearRoomUC;
import br.com.nathan.hotel.core.usecase.SetRoomTakenUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClearRoomTakenEventListener implements ApplicationListener<ClearRoomTaken> {

    private final ClearRoomUC clearRoomUC;

    @Override
    public void onApplicationEvent(ClearRoomTaken event) {
        clearRoomUC.execute(event.getRoomId());
    }
}
