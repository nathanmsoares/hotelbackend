package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.command.CreateParkingCommand;
import br.com.nathan.hotel.core.dto.event.CreateParkingEvent;
import br.com.nathan.hotel.core.usecase.CreateParkingUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateParkingEventListener implements ApplicationListener<CreateParkingEvent> {

    private final CreateParkingUC createParkingUC;

    @Override
    public void onApplicationEvent(CreateParkingEvent event) {
        if (event.getParkingRequested()) {
            createParkingUC.execute(new CreateParkingCommand(event.getReservation()));
        }
    }
}
