package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class SetRoomReservationExpenseUC {

    private final RoomReservationRepository roomReservationRepository;

    public void execute(List<RoomReservation> roomReservationList) {
        roomReservationList.forEach(RoomReservation::setExpenseFirstDay);
        roomReservationRepository.saveAllRoomReservation(roomReservationList);
    }

}
