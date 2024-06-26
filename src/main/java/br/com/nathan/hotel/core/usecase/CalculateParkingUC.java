package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CalculateParkingUC {

    private final ParkingRepository parkingRepository;

    @Transactional
    public void execute() {
        log.info("Calculate Room Reservation price for the day on {}", LocalDateTime.now());
        List<Parking> parkingList = parkingRepository.findAllByClosedAndReservationCheckInIsNotNull(Boolean.FALSE);
        parkingList.forEach(Parking::addExpenseToTheDay);
        parkingRepository.saveAllParking(parkingList);
    }

}
