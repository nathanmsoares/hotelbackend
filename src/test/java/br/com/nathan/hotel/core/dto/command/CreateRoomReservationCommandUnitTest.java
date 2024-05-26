package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.entity.RoomReservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateRoomReservationCommandUnitTest {

    @Test
    public void createRoomReservation() {
        CreateRoomReservationCommand command = new CreateRoomReservationCommand(new Reservation(), new Room());
        RoomReservation roomReservation = command.toEntity();
        Assertions.assertEquals(command.getReservation(), roomReservation.getReservation());
        Assertions.assertEquals(command.getRoom(), roomReservation.getRoom());
        Assertions.assertNotNull(roomReservation.getExpense());
        Assertions.assertTrue(roomReservation.getExpense() > 0d);
    }
}
