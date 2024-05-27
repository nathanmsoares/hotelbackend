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
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class CalculateRoomReservationUCIT {

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private CalculateRoomReservationUC calculateRoomReservationUC;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    public void setup() {
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

        CreateRoomReservationCommand createRoomReservationCommand = new CreateRoomReservationCommand(reservation, room);
        roomReservationRepository.save(createRoomReservationCommand.toEntity());
    }

    @AfterEach
    public void tearDown() {
        roomReservationRepository.deleteAll();
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    @DisplayName("Should add values to expense")
    public void addRoomReservationExpensesOnWeekDay() {
        List<RoomReservation> roomReservationList = roomReservationRepository.findAllByPaid(Boolean.FALSE);
        calculateRoomReservationUC.execute();
        Assertions.assertTrue(roomReservationList.stream().noneMatch(
                roomReservation -> roomReservationRepository.findById(roomReservation.getId()).get().getExpense()
                        .equals(roomReservation.getExpense())
        ));
    }
}
