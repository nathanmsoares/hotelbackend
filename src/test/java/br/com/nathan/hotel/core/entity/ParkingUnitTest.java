package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.dto.command.CreateParkingCommand;
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
        Assertions.assertEquals(Boolean.FALSE, parking.getClosed());
    }


    @Test
    @DisplayName("Should add to expense on Weekend")
    public void addParkingExpensesOnWeekendDay() {
        LocalDateTime tomorrow = LocalDateTime.of(2024, 5, 25, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(tomorrow);
            Parking parking = new CreateParkingCommand(Reservation.builder().id(10L).checkIn(tomorrow.minusDays(1)).build()).toEntity();
            parking.setExpenseFirstDay();
            Double expenseBefore = parking.getExpense();
            parking.addExpenseToTheDay();
            Double expenseAfter = parking.getExpense();
            Assertions.assertEquals(20d, expenseAfter - expenseBefore);
        }
    }

    @Test
    @DisplayName("Should add to expense on Week day")
    public void addParkingExpensesOnWeekDay() {
        LocalDateTime tomorrow = LocalDateTime.of(2024, 5, 27, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(tomorrow);
            Parking parking = new CreateParkingCommand(Reservation.builder().id(10L).checkIn(tomorrow.minusDays(1)).build()).toEntity();
            parking.setExpenseFirstDay();
            Double expenseBefore = parking.getExpense();
            parking.addExpenseToTheDay();
            Double expenseAfter = parking.getExpense();
            Assertions.assertEquals(15d, expenseAfter - expenseBefore);
        }
    }

    @Test
    @DisplayName("Should reduce expense on Week day after 12 hour")
    public void shouldReduceParkingExpensesOnWeekDay() {
        LocalDateTime tomorrow = LocalDateTime.of(2024, 5, 27, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(tomorrow);
            Parking parking = new CreateParkingCommand(Reservation.builder().id(10L).checkIn(tomorrow.minusDays(1)).build()).toEntity();
            parking.setExpenseFirstDay();
            Double expenseBefore = parking.getExpense();
            Assertions.assertFalse(parking.getClosed());
            parking.checkOut();
            Double expenseAfter = parking.getExpense();
            Assertions.assertEquals(7.5, expenseBefore - expenseAfter);
            Assertions.assertTrue(parking.getClosed());
        }
    }

    @Test
    @DisplayName("Should reduce expense on Weekend day after 12 hour")
    public void shouldReduceParkingExpensesOnWeekendDay() {
        LocalDateTime tomorrow = LocalDateTime.of(2024, 5, 26, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(tomorrow);
            Parking parking = new CreateParkingCommand(
                    Reservation.builder().id(10L).checkIn(tomorrow.minusDays(1)).build()).toEntity();
            parking.setExpenseFirstDay();
            Double expenseBefore = parking.getExpense();
            Assertions.assertFalse(parking.getClosed());
            parking.checkOut();
            Double expenseAfter = parking.getExpense();
            Assertions.assertEquals(10, expenseBefore - expenseAfter);
            Assertions.assertTrue(parking.getClosed());
        }
    }

    @Test
    @DisplayName("Should not reduce expense on Week day")
    public void shouldNotReduceParkingExpensesOnWeekDay() {
        LocalDateTime tomorrow = LocalDateTime.of(2024, 5, 27, 11, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(tomorrow);
            Parking parking = new CreateParkingCommand(Reservation.builder().id(10L).checkIn(tomorrow.minusDays(1)).build()).toEntity();
            parking.setExpenseFirstDay();
            Double expenseBefore = parking.getExpense();
            Assertions.assertFalse(parking.getClosed());
            parking.checkOut();
            Double expenseAfter = parking.getExpense();
            Assertions.assertEquals(0, expenseBefore - expenseAfter);
            Assertions.assertTrue(parking.getClosed());
        }
    }

}
