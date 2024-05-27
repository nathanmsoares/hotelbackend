package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.dto.command.CreateParkingCommand;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class CalculateParkingUCIT {

    @Autowired
    private CalculateParkingUC calculateParkingUC;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    public void setup() {
        CreateGuestCommand createGuestCommand =
                new CreateGuestCommand("name", "+554799999999", "100999999-20");
        Guest guest = guestRepository.save(createGuestCommand.toEntity());
        CreateReservationCommand createReservationCommand = new CreateReservationCommand(List.of(guest));
        Reservation reservation = reservationRepository.save(createReservationCommand.toEntity());
        Room room = roomRepository.saveAndFlush(Room.builder().number(10).floor(99).build());
        CreateRoomReservationCommand createRoomReservationCommand = new CreateRoomReservationCommand(reservation, room);
        roomReservationRepository.saveAndFlush(createRoomReservationCommand.toEntity());
        parkingRepository.save(new CreateParkingCommand(reservation).toEntity());
        reservation = reservationRepository.findById(reservation.getId()).get();
        reservation.checkInHotel();
        reservationRepository.save(reservation);
    }

    @AfterEach
    public void tearDown() {
        parkingRepository.deleteAll();
        roomReservationRepository.deleteAll();
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("Should add values to expense")
    public void addRoomReservationExpensesOnWeekDay() {
        List<Parking> parkingList = parkingRepository.findAllByClosedAndReservationCheckInIsNotNull(Boolean.FALSE);
        calculateParkingUC.execute();
        Assertions.assertTrue(parkingList.stream().noneMatch(
                parking -> parkingRepository.findById(parking.getId()).get().getExpense()
                        .equals(parking.getExpense())
        ));
    }
}
