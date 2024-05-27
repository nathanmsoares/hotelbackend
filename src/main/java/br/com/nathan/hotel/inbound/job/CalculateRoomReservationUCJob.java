package br.com.nathan.hotel.inbound.job;

import br.com.nathan.hotel.core.usecase.CalculateParkingUC;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateRoomReservationUCJob {

    private final CalculateParkingUC calculateParkingUC;

    @Transactional
    @Scheduled(cron = "0 0 12 * * *")
    @SchedulerLock(name = "TaskScheduler_CalculateRoomReservationUCJob",
            lockAtLeastFor = "PT5M", lockAtMostFor = "PT15M")
    public void execute() {
        calculateParkingUC.execute();
    }

}
