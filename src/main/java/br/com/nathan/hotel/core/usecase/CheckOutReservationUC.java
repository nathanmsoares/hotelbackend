package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.ReservationDTO;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.exception.ReservationNotFoundException;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CheckOutReservationUC {

    private final ReservationRepository reservationRepository;

    @Transactional
    public Reservation execute(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        Reservation reservation =
                reservationOptional.orElseThrow(() -> new ReservationNotFoundException("Reserva não encontrada"));
        reservation.checkOut();
        reservationRepository.save(reservation);
        reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reserva não encontrada"));
        reservation.setTotalCostAfterCheckOut();
        reservationRepository.save(reservation);
        return reservationRepository.findById(reservationId).get();
    }
}
