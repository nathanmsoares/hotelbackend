package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

@Slf4j
public class RoomReservationUnitTest {

    @Test
    @DisplayName("Should add to expense on Weekend")
    public void addRoomReservationExpensesOnWeekendDay() {
        LocalDateTime today = LocalDateTime.of(2024, 5, 25, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(today);
            RoomReservation roomReservation = new CreateRoomReservationCommand(new Reservation(), new Room()).toEntity();
            roomReservation.setExpenseFirstDay();
            Double expenseBefore = roomReservation.getExpense();
            roomReservation.addExpenseToTheDay();
            Double expenseAfter = roomReservation.getExpense();
            Assertions.assertEquals(180d, expenseAfter - expenseBefore);
        }
    }

    @Test
    @DisplayName("Should add to expense on Week day")
    public void addRoomReservationExpensesOnWeekDay() {
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(today);
            RoomReservation roomReservation = new CreateRoomReservationCommand(new Reservation(), new Room()).toEntity();
            roomReservation.setExpenseFirstDay();
            Double expenseBefore = roomReservation.getExpense();
            roomReservation.addExpenseToTheDay();
            Double expenseAfter = roomReservation.getExpense();
            Assertions.assertEquals(120d, expenseAfter - expenseBefore);
        }
    }

    @Test
    @DisplayName("Should not reduce expense on Week day after 12 hour when check-out is on the same day")
    public void shouldNotReduceExpensesOnWeekDay() {
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 20, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(today);
            RoomReservation roomReservation = new
                    CreateRoomReservationCommand(Reservation.builder().checkIn(today).build(), new Room()).toEntity();
            roomReservation.setExpenseFirstDay();
            Double expenseBefore = roomReservation.getExpense();
            Assertions.assertFalse(roomReservation.getClosed());
            roomReservation.checkOut();
            Double expenseAfter = roomReservation.getExpense();
            Assertions.assertEquals(expenseBefore, expenseAfter);
            Assertions.assertTrue(roomReservation.getClosed());
        }
    }

    @Test
    @DisplayName("Should not reduce expense on Week day after 12 hour when hour is before checkout hour")
    public void shouldNotReduceExpensesOnWeekDayBeforeHour() {
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 11, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(today);
            RoomReservation roomReservation = new
                    CreateRoomReservationCommand(Reservation.builder().checkIn(today).build(), new Room()).toEntity();
            roomReservation.setExpenseFirstDay();
            Double expenseBefore = roomReservation.getExpense();
            Assertions.assertFalse(roomReservation.getClosed());
            roomReservation.checkOut();
            Double expenseAfter = roomReservation.getExpense();
            Assertions.assertEquals(expenseBefore, expenseAfter);
            Assertions.assertTrue(roomReservation.getClosed());
        }
    }

    @Test
    @DisplayName("Should reduce expense on Week day after 12 hour")
    public void shouldReduceExpensesOnWeekDayBeforeHour() {
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 11, 00);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(today);
            RoomReservation roomReservation = new
                    CreateRoomReservationCommand(Reservation.builder().checkIn(today.plusDays(1)).build(), new Room()).toEntity();
            roomReservation.setExpenseFirstDay();
            Double expenseBefore = roomReservation.getExpense();
            Assertions.assertFalse(roomReservation.getClosed());
            roomReservation.checkOut();
            Double expenseAfter = roomReservation.getExpense();
            Assertions.assertEquals(0, expenseAfter - expenseBefore);
            Assertions.assertTrue(roomReservation.getClosed());
        }
    }


}
