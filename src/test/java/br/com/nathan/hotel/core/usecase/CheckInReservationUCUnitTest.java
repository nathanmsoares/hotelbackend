package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.exception.ReservationNotFoundException;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Slf4j
public class CheckInReservationUCUnitTest {

    private final TimeZone timeZone = TimeZone.getDefault();

    private CheckInReservationUC checkInReservationUC;

    @Mock
    private ReservationRepository reservationRepository;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        checkInReservationUC = new CheckInReservationUC(reservationRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
        System.setProperty("user.timezone", timeZone.getID());
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone.getID()));
    }

    @Test
    @DisplayName("Should checkIn")
    public void shouldCheckIn() {
        setZoneTimeAfter14Hour();
        Reservation reservation = Reservation.builder()
                .id(10L)
                .roomReservationList(List.of(new RoomReservation()))
                .build();
        Mockito.when(reservationRepository.findById(10L)).thenReturn(Optional.ofNullable(reservation));
        checkInReservationUC.execute(reservation.getId());
        Mockito.verify(reservationRepository, Mockito.times(1)).save(reservation);
        Assertions.assertNotNull(reservation.getCheckIn());
    }

    @Test
    @DisplayName("Should Thow when Reservation not found")
    public void shouldThrowCheckIn() {
        setZoneTimeAfter14Hour();
        Reservation reservation = Reservation.builder()
                .id(10L)
                .roomReservationList(List.of(new RoomReservation()))
                .build();
        try {
            checkInReservationUC.execute(11L);
        } catch (ReservationNotFoundException reservationNotFoundException) {
            org.assertj.core.api.Assertions.assertThat(reservationNotFoundException)
                    .isInstanceOf(ReservationNotFoundException.class)
                    .hasMessage("Reserva não encontrada");
        }
        Mockito.verify(reservationRepository, Mockito.times(0)).save(reservation);
        Assertions.assertNull(reservation.getCheckIn());
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

    private void setZoneTimeAfter14Hour() {
        LocalDateTime now = LocalDateTime.now();
        if (isBefore14Hour(now)) {
            String zone = getTimeZoneId(15, now);
            System.setProperty("user.timezone", zone);
            TimeZone.setDefault(TimeZone.getTimeZone(zone));
        }
    }

    private Boolean isBefore14Hour(LocalDateTime now) {
        return now.isBefore(LocalDateTime.now().toLocalDate().atTime(LocalTime.of(14, 0)));
    }
}
