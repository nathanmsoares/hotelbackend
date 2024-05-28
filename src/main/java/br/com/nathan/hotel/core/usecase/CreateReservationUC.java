package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.dto.event.CreateParkingEvent;
import br.com.nathan.hotel.core.dto.event.CreateRoomReservationEvent;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateReservationUC {

    private final ReservationRepository reservationRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public Reservation execute(CreateReservationCommand command) {
        Reservation reservation = reservationRepository.save(command.toEntity());
        log.info("Saved Reservation id {} on Guest Ids {}", reservation.getId(),
                reservation.getGuestList().stream().map(Guest::getId).toList());
        applicationEventPublisher.publishEvent(new CreateRoomReservationEvent(reservation, command.getRoom()));
        applicationEventPublisher.publishEvent(new CreateParkingEvent(reservation, command.getParkingRequested()));
        return reservation;
    }
}
