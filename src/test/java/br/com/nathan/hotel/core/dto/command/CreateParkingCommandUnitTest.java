package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.entity.Reservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateParkingCommandUnitTest {

    @Test
    @DisplayName("Should create Parking")
    public void createParking() {
        CreateParkingCommand command = new CreateParkingCommand(new Reservation());
        Parking parking = command.toEntity();
        Assertions.assertEquals(command.getReservation(), parking.getReservation());
        Assertions.assertEquals(Boolean.FALSE, parking.getClosed());
    }
}
