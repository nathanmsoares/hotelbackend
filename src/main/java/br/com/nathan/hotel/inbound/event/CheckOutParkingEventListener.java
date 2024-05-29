package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.event.CheckOutParkingEvent;
import br.com.nathan.hotel.core.usecase.CheckOutParkingUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckOutParkingEventListener implements ApplicationListener<CheckOutParkingEvent> {

    private final CheckOutParkingUC checkOutParkingUC;

    @Override
    public void onApplicationEvent(CheckOutParkingEvent event) {
        checkOutParkingUC.execute(event.getParkingId());
    }
}
