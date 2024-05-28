package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import br.com.nathan.hotel.core.repository.RoomRepository;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@RecordApplicationEvents
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

    @Autowired
    private CreateReservationUC createReservationUC;

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

        reservation = createReservationUC.execute(new CreateReservationCommand(List.of(guest.toDTO()), Boolean.FALSE, room));
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
        Reservation reservationFound = reservationRepository.findById(reservation.getId()).get();
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(today);
            reservationFound.checkInHotel();
            reservationRepository.save(reservationFound);
            List<RoomReservation> roomReservationList = roomReservationRepository.findAllByClosedAndReservationCheckInIsNotNull(Boolean.FALSE);
            calculateRoomReservationUC.execute();
            Assertions.assertTrue(roomReservationList.stream().noneMatch(
                    roomReservation -> roomReservationRepository.findById(roomReservation.getId()).get().getExpense()
                            .equals(roomReservation.getExpense())
            ));
        }
    }
}
