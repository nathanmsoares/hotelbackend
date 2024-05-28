package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.event.SetRoomTaken;
import br.com.nathan.hotel.core.usecase.ClearRoomUC;
import br.com.nathan.hotel.core.usecase.SetRoomTakenUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SetRoomTakenEventListener implements ApplicationListener<SetRoomTaken> {

    private final SetRoomTakenUC setRoomTakenUC;

    @Override
    public void onApplicationEvent(SetRoomTaken event) {
        setRoomTakenUC.execute(event.getRoomId());
    }
}
