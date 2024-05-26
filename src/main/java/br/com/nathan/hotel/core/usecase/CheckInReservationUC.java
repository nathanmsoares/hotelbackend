package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.exception.ReservationNotFoundException;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CheckInReservationUC {

    private ReservationRepository reservationRepository;

    public void execute(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        Reservation reservation =
                reservationOptional.orElseThrow(() -> new ReservationNotFoundException("Reserva não encontrada"));
        reservation.checkInHotel();
        reservationRepository.save(reservation);
    }
}
