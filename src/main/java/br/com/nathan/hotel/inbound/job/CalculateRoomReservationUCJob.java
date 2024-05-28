package br.com.nathan.hotel.inbound.job;

import br.com.nathan.hotel.core.usecase.CalculateRoomReservationUC;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateRoomReservationUCJob {

    private final CalculateRoomReservationUC calculateRoomReservationUC;

    @Transactional
    @Scheduled(cron = "0 5 * * * *")
    @SchedulerLock(name = "TaskScheduler_CalculateRoomReservationUCJob",
            lockAtLeastFor = "PT5M", lockAtMostFor = "PT15M")
    public void execute() {
        calculateRoomReservationUC.execute();
    }
}
