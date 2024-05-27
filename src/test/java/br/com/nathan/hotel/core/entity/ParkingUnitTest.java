package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.dto.command.CreateParkingCommand;
import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

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


    @Test
    @DisplayName("Should add to expense on Weekend")
    public void addRoomReservationExpensesOnWeekendDay() {
        LocalDateTime today = LocalDateTime.of(2024, 5, 25, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(today);
            Parking parking = new CreateParkingCommand(Reservation.builder().id(10L).build()).toEntity();
            Double expenseBefore = parking.getExpense();
            parking.addExpenseToTheDay();
            Double expenseAfter = parking.getExpense();
            Assertions.assertEquals(20d, expenseAfter - expenseBefore);
        }
    }

    @Test
    @DisplayName("Should add to expense on Week day")
    public void addRoomReservationExpensesOnWeekDay() {
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(today);
            Parking parking = new CreateParkingCommand(Reservation.builder().id(10L).build()).toEntity();
            Double expenseBefore = parking.getExpense();
            parking.addExpenseToTheDay();
            Double expenseAfter = parking.getExpense();
            Assertions.assertEquals(15d, expenseAfter - expenseBefore);
        }
    }

}
