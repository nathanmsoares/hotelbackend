package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.exception.CheckInNotAllowedException;
import br.com.nathan.hotel.core.exception.RoomReservationEmptyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.List;
import java.util.TimeZone;

public class ReservationUnitTest {

    private static final Logger log = LoggerFactory.getLogger(ReservationUnitTest.class);
    @Mock
    private Reservation reservation;

    private final TimeZone timeZone = TimeZone.getDefault();

    @AfterEach
    public void tearDown() {
        System.setProperty("user.timezone", timeZone.getID());
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone.getID()));
    }

    @Test
    @DisplayName("Should CheckIn")
    public void shouldCheckIn() {
        Reservation reservation = Reservation.builder()
                .id(10L)
                .guestList(List.of(Guest.builder().id(9L).build()))
                .roomReservationList(List.of(RoomReservation.builder().id(11L).build()))
                .build();
        setZoneTimeAfter14Hour();
        reservation.checkInHotel();
        Assertions.assertNotNull(reservation.getCheckIn());
    }

    @Test
    @DisplayName("Should not CheckIn Before 14")
    public void shouldNotCheckIn() {
        Reservation reservation = Reservation.builder()
                .id(10L)
                .guestList(List.of(Guest.builder().id(9L).build()))
                .roomReservationList(List.of(RoomReservation.builder().id(11L).build()))
                .build();
        setZoneTimeBefore14();
        org.assertj.core.api.Assertions.assertThatThrownBy(reservation::checkInHotel)
                .isInstanceOf(CheckInNotAllowedException.class)
                .hasMessage("Antes das 14:00 não é possivel realizar o Check-In");
        Assertions.assertNull(reservation.getCheckIn());
    }

    @Test
    @DisplayName("Should not CheckIn When already Checked In")
    public void shouldNotCheckInAlreadyCheckedIn() {
        Reservation reservation = Reservation.builder()
                .id(10L)
                .guestList(List.of(Guest.builder().id(9L).build()))
                .checkIn(LocalDateTime.now())
                .build();
        setZoneTimeAfter14Hour();
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
        setZoneTimeAfter14Hour();
        org.assertj.core.api.Assertions.assertThatThrownBy(reservation::checkInHotel)
                .isInstanceOf(RoomReservationEmptyException.class)
                .hasMessage("Não há Reservas de quartos para a Reserva");
        Assertions.assertNull(reservation.getCheckIn());
    }


    public void setZoneTimeBefore14() {
        LocalDateTime now = LocalDateTime.now();
        if (!isBefore14Hour(now)) {
            String zone = getTimeZoneId(13, now);
            System.setProperty("user.timezone", zone);
            TimeZone.setDefault(TimeZone.getTimeZone(zone));
        }
    }


    private void setZoneTimeAfter14Hour() {
        LocalDateTime now = LocalDateTime.now();
        if (isBefore14Hour(now)) {
            String zone = getTimeZoneId(15, now);
            System.setProperty("user.timezone", zone);
            TimeZone.setDefault(TimeZone.getTimeZone(zone));
        }
    }

    private String getTimeZoneId(Integer hourToCompare, LocalDateTime now) {
        for (String availableID : TimeZone.getAvailableIDs()) {
            try {
                LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(availableID));
                if (Integer.valueOf(hourToCompare).equals(localDateTime.getHour())) {
                    return availableID;
                }
            } catch (ZoneRulesException zoneRulesException) {
                log.error("Timezone not found: {}", zoneRulesException.getMessage());
            }
        }
        throw new RuntimeException("None TimeZone id found");
    }

    private Boolean isBefore14Hour(LocalDateTime now) {
        return now.isBefore(LocalDateTime.now().toLocalDate().atTime(LocalTime.of(14, 0)));
    }

}
