package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.exception.ReservationNotFoundException;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import br.com.nathan.hotel.core.repository.RoomRepository;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CheckOutReservationRelatedUC {

    private final ReservationRepository reservationRepository;

    private final RoomReservationRepository roomReservationRepository;

    private final ParkingRepository parkingRepository;

    private final RoomRepository roomRepository;

    private final ClearRoomUC clearRoomUC;

    @Transactional
    public void execute(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        Reservation reservation =
                reservationOptional.orElseThrow(() -> new ReservationNotFoundException("Reserva não encontrada"));

        Optional.ofNullable(reservation.getParking()).ifPresent(Parking::checkOut);
        Optional.ofNullable(reservation.getRoomReservation()).ifPresent(RoomReservation::checkOut);
        parkingRepository.save(reservation.getParking());
        roomReservationRepository.save(reservation.getRoomReservation());
        clearRoomUC.execute(reservation.getRoomReservation().getRoom().getId());
    }
}
