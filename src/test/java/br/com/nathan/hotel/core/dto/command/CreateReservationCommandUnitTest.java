package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CreateReservationCommandUnitTest {

    @Test
    @DisplayName("Should create Reservation")
    public void createReservation() {
        CreateReservationCommand command = new CreateReservationCommand(List.of(new Guest()), Boolean.FALSE, new Room());
        Reservation reservation = command.toEntity();
        Assertions.assertEquals(command.getGuestList(), reservation.getGuestList());
    }

}
