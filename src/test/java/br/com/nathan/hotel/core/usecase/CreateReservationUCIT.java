package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.*;
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

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @AfterEach
    public void tearDown() {
        parkingRepository.deleteAll();
        roomReservationRepository.deleteAll();
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a Reservation without Parking")
    @Transactional
    public void createReservation() {
        CreateGuestCommand createGuestCommand =
                new CreateGuestCommand("name", "+554799999999", "100999999-20");
        Guest guest = guestRepository.save(createGuestCommand.toEntity());
        Room room = Room.builder()
                .number(10)
                .floor(99)
                .build();
        room = roomRepository.save(room);
        CreateReservationCommand createReservationCommand = new CreateReservationCommand(List.of(guest.toDTO()), Boolean.FALSE, room);
        Reservation reservation = createReservationUC.execute(createReservationCommand);

        Optional<Reservation> reservationOptional = reservationRepository.findById(reservation.getId());
        Assertions.assertTrue(reservationOptional.isPresent());

        reservation = reservationOptional.get();
        Assertions.assertEquals(guest.getId(), reservation.getGuestList().get(0).getId());
        Assertions.assertEquals(1,
                roomReservationRepository.findAllByReservationId(reservation.getId()).size());
        Assertions.assertTrue(parkingRepository.findAllByReservationId(reservation.getId()).isEmpty());
    }

    @Test
    @DisplayName("Should create a Reservation with Parking")
    @Transactional
    public void createReservationWithParking() {
        CreateGuestCommand createGuestCommand =
                new CreateGuestCommand("name", "+554799999999", "100999999-20");
        Guest guest = guestRepository.save(createGuestCommand.toEntity());
        Room room = Room.builder()
                .number(10)
                .floor(99)
                .build();
        room = roomRepository.save(room);
        CreateReservationCommand createReservationCommand = new CreateReservationCommand(List.of(guest.toDTO()), Boolean.TRUE, room);
        Reservation reservation = createReservationUC.execute(createReservationCommand);

        Optional<Reservation> reservationOptional = reservationRepository.findById(reservation.getId());
        Assertions.assertTrue(reservationOptional.isPresent());

        reservation = reservationOptional.get();
        Assertions.assertEquals(guest.getId(), reservation.getGuestList().get(0).getId());
        Assertions.assertEquals(1,
                roomReservationRepository.findAllByReservationId(reservation.getId()).size());
        Assertions.assertEquals(1,
                parkingRepository.findAllByReservationId(reservation.getId()).size());
    }

}
