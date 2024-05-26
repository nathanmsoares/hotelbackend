package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateReservationUC {

    private final ReservationRepository reservationRepository;

    public Reservation execute(CreateReservationCommand command) {
        Reservation reservation = reservationRepository.save(command.toEntity());
        log.info("Saved Reservation id {} on Guest Ids {}", reservation.getId(),
                reservation.getGuestList().stream().map(Guest::getId).toList());
        return reservation;
    }
}
