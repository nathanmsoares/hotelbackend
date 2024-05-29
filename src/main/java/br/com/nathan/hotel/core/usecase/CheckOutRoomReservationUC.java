package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.exception.ParkingNotFoundException;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CheckOutRoomReservationUC {

    private final RoomReservationRepository roomReservationRepository;

    public void execute(Long roomReservationId) {
        Optional<RoomReservation> reservationOptional = roomReservationRepository.findById(roomReservationId);
        RoomReservation roomReservation = reservationOptional
                .orElseThrow(() -> new ParkingNotFoundException("Estacionamento não encontrado"));
        roomReservation.checkOut();
        roomReservationRepository.save(roomReservation);
    }
}
