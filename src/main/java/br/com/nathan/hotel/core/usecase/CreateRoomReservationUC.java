package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateRoomReservationUC {

    private final RoomReservationRepository roomReservationRepository;

    public RoomReservation execute(CreateRoomReservationCommand command) {
        RoomReservation roomReservation = roomReservationRepository.save(command.toEntity());
        log.info("Saved Room Reservation Id {}, on Reservation id {}", roomReservation.getId(),
                roomReservation.getReservation().getId());
        return roomReservation;
    }
}
