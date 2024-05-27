package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.exception.CheckInNotAllowedException;
import br.com.nathan.hotel.core.exception.RoomReservationEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.List;
import java.util.TimeZone;

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
            Double expenseBefore = roomReservation.getExpense();
            roomReservation.addExpenseToTheDay();
            Double expenseAfter = roomReservation.getExpense();
            Assertions.assertEquals(120d, expenseAfter - expenseBefore);
        }
    }

}
