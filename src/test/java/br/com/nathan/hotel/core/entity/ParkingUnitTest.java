package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.dto.command.CreateParkingCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParkingUnitTest {

    @Test
    @DisplayName("Should create Parking")
    public void createParking() {
        CreateParkingCommand command = new CreateParkingCommand(new Reservation());
        Parking parking = command.toEntity();
        Assertions.assertEquals(command.getReservation(), parking.getReservation());
        Assertions.assertNotNull(parking.getExpense());
        Assertions.assertEquals(Boolean.FALSE, parking.getPaid());
        Assertions.assertTrue(parking.getExpense() > 0d);
    }

}
