package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.exception.CheckInNotAllowedException;
import br.com.nathan.hotel.core.exception.RoomReservationEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class ReservationUnitTest {

    @Test
    @DisplayName("Should CheckIn")
    public void shouldCheckIn() {
        Reservation reservation = Reservation.builder()
                .id(10L)
                .guestList(List.of(Guest.builder().id(9L).build()))
                .roomReservationList(List.of(RoomReservation.builder().id(11L).build()))
                .build();
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 20, 00);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(today);
            reservation.checkInHotel();
            Assertions.assertNotNull(reservation.getCheckIn());
        }
    }

    @Test
    @DisplayName("Should not CheckIn Before 14")
    public void shouldNotCheckIn() {
        Reservation reservation = Reservation.builder()
                .id(10L)
                .guestList(List.of(Guest.builder().id(9L).build()))
                .roomReservationList(List.of(RoomReservation.builder().id(11L).build()))
                .build();
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 11, 00);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(today);

            org.assertj.core.api.Assertions.assertThatThrownBy(reservation::checkInHotel)
                    .isInstanceOf(CheckInNotAllowedException.class)
                    .hasMessage("Não é possivel realizar o Check-In neste horário");
            Assertions.assertNull(reservation.getCheckIn());
        }
    }

    @Test
    @DisplayName("Should not CheckIn When already Checked In")
    public void shouldNotCheckInAlreadyCheckedIn() {
        Reservation reservation = Reservation.builder()
                .id(10L)
                .guestList(List.of(Guest.builder().id(9L).build()))
                .checkIn(LocalDateTime.now())
                .build();

        org.assertj.core.api.Assertions.assertThatThrownBy(reservation::checkInHotel)
                .isInstanceOf(CheckInNotAllowedException.class)
                .hasMessage("Check-In já realizado");
        Assertions.assertNotNull(reservation.getCheckIn());
    }

    @Test
    @DisplayName("Should not CheckIn When No Room Reservation")
    public void shouldNotCheckInWithoutRoomReservation() {
        Reservation reservation = Reservation.builder()
                .id(10L)
                .guestList(List.of(Guest.builder().id(9L).build()))
                .build();
        org.assertj.core.api.Assertions.assertThatThrownBy(reservation::checkInHotel)
                .isInstanceOf(RoomReservationEmptyException.class)
                .hasMessage("Não há Reservas de quartos para a Reserva");
        Assertions.assertNull(reservation.getCheckIn());
    }
}
