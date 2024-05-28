package br.com.nathan.hotel.inbound.event;

import br.com.nathan.hotel.core.dto.event.CheckInParkingExpenseEvent;
import br.com.nathan.hotel.core.usecase.SetParkingExpenseUC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckInParkingExpenseEventListener implements ApplicationListener<CheckInParkingExpenseEvent> {

    private final SetParkingExpenseUC setParkingExpenseUC;

    @Override
    public void onApplicationEvent(CheckInParkingExpenseEvent event) {
        setParkingExpenseUC.execute(event.getParking());
    }
}
