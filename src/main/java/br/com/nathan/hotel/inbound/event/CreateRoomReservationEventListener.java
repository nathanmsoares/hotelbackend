package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.dto.event.CreateRoomReservationEvent;
import br.com.nathan.hotel.core.usecase.CreateRoomReservationUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateRoomReservationEventListener implements ApplicationListener<CreateRoomReservationEvent> {

    private final CreateRoomReservationUC createRoomReservationUC;

    @Override
    public void onApplicationEvent(CreateRoomReservationEvent event) {
        createRoomReservationUC.execute(new CreateRoomReservationCommand(event.getReservation(), event.getRoom()));
    }
}
