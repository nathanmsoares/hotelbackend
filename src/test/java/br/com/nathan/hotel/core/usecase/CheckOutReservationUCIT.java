package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.List;

@SpringBootTest
@RecordApplicationEvents
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class CheckOutReservationUCIT {

    @Autowired
    private CheckOutReservationUC checkOutReservationUC;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CreateReservationUC createReservationUC;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    private Reservation reservation;

    @BeforeEach
    public void setup() {
        Room room = Room.builder()
                .number(10)
                .floor(99)
                .build();
        room = roomRepository.saveAndFlush(room);

        Guest guest = Guest.builder()
                .name("name")
                .cpf("10099999920")
                .telephone("+5547999999999")
                .build();
        guest = guestRepository.saveAndFlush(guest);

        reservation = createReservationUC.execute(new CreateReservationCommand(List.of(guest.toDTO()), Boolean.TRUE, room));
        reservation = reservationRepository.findById(reservation.getId()).get();
        reservation.checkInHotel();
    }

    @AfterEach
    public void tearDown() {
        parkingRepository.deleteAll();
        roomReservationRepository.deleteAll();
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    public void shouldCheckOut() {
        reservation.checkOut();
    }

}
