package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import br.com.nathan.hotel.core.repository.RoomRepository;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
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
public class CreateRoomReservationUCIT {

    @Autowired
    private CreateRoomReservationUC createRoomReservationUC;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
    public void tearDown() {
        roomReservationRepository.deleteAll();
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a Room Reservation")
    @Transactional
    public void createRoomReservation() {
        Room room = Room.builder()
                .number(10)
                .floor(99)
                .build();
        room = roomRepository.save(room);

        Guest guest = Guest.builder()
                .name("name")
                .cpf("10099999920")
                .telephone("+5547999999999")
                .build();
        guest = guestRepository.save(guest);

        Reservation reservation = Reservation.builder()
                .guestList(List.of(guest))
                .build();
        reservation = reservationRepository.save(reservation);

        CreateRoomReservationCommand command = new CreateRoomReservationCommand(reservation, room);
        RoomReservation roomReservation = createRoomReservationUC.execute(command);

        Optional<RoomReservation> roomReservationOptional = roomReservationRepository.findById(roomReservation.getId());
        Assertions.assertTrue(roomReservationOptional.isPresent());

        roomReservation = roomReservationOptional.get();

        Assertions.assertEquals(reservation, roomReservation.getReservation());
        Assertions.assertEquals(room, roomReservation.getRoom());
    }

}
