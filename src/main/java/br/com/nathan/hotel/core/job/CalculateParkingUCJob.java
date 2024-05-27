package br.com.nathan.hotel.core.job;

import br.com.nathan.hotel.core.usecase.CalculateParkingUC;
import br.com.nathan.hotel.core.usecase.CalculateRoomReservationUC;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateParkingUCJob {

    private final CalculateRoomReservationUC calculateRoomReservationUC;

    @Transactional
    @Scheduled(cron = "0 0 12 * * *")
    public void execute() {
        calculateRoomReservationUC.execute();
    }
}
