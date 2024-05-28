package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.event.CheckOutReservationEvent;
import br.com.nathan.hotel.core.usecase.CheckOutReservationRelatedUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckOutReservationEventListener implements ApplicationListener<CheckOutReservationEvent> {

    private final CheckOutReservationRelatedUC checkOutReservationRelatedUC;

    @Override
    public void onApplicationEvent(CheckOutReservationEvent event) {
        checkOutReservationRelatedUC.execute(event.getReservationId());
    }
}
