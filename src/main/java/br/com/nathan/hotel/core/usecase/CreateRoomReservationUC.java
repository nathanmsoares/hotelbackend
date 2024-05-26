package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateRoomReservationUC {

    private final RoomReservationRepository reservationRepository;

    public RoomReservation execute(CreateRoomReservationCommand command) {

    }
}
