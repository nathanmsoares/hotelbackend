package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class CreateReservationUCIT {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private CreateReservationUC createReservationUC;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    public void tearDown() {
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a Reservation")
    @Transactional
    public void createReservation() {
        CreateGuestCommand createGuestCommand =
                new CreateGuestCommand("name", "+554799999999", "100999999-20");
        Guest guest = guestRepository.save(createGuestCommand.toEntity());
        CreateReservationCommand createReservationCommand = new CreateReservationCommand(List.of(guest));
        Reservation reservation = createReservationUC.execute(createReservationCommand);

        Optional<Reservation> reservationOptional = reservationRepository.findById(reservation.getId());
        Assertions.assertTrue(reservationOptional.isPresent());

        reservation = reservationOptional.get();
        Assertions.assertEquals(guest, reservation.getGuestList().get(0));
    }

}
