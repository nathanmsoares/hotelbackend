package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateParkingCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.ParkingRepository;
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
public class CreateParkingUCIT {

    @Autowired
    private CreateParkingUC createParkingUC;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @AfterEach
    public void tearDown() {
        parkingRepository.deleteAll();
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a Parking Reservation")
    @Transactional
    public void createParking() {
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

        CreateParkingCommand createParkingCommand = new CreateParkingCommand(reservation);
        Parking parking = createParkingUC.execute(createParkingCommand);

        Optional<Parking> parkingOptional = parkingRepository.findById(parking.getId());
        Assertions.assertTrue(parkingOptional.isPresent());

        parking = parkingOptional.get();
        Assertions.assertEquals(reservation, parking.getReservation());
    }

}
