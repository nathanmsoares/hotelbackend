package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CalculateRoomReservationUC {

    private final RoomReservationRepository roomReservationRepository;

    @Transactional
    public void execute() {
        log.info("Calculate Room Reservation price for the day on {}", LocalDateTime.now());
        List<RoomReservation> roomReservationList = roomReservationRepository.findAllByClosedAndReservationCheckInIsNotNull(Boolean.FALSE);
        roomReservationList.forEach(RoomReservation::addExpenseToTheDay);
        roomReservationRepository.saveAllRoomReservation(roomReservationList);
    }

}
