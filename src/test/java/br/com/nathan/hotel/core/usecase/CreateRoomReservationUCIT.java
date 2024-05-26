package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import br.com.nathan.hotel.core.repository.RoomRepository;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

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
        createRoomReservationUC.execute(command);

    }



}
