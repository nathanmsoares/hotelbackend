package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.exception.ReservationNotFoundException;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CheckInReservationUCUnitTest {

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
    }

    @Test
    @DisplayName("Should checkIn")
    public void shouldCheckIn() {
        LocalDateTime today = LocalDateTime.of(2024, 5, 27, 20, 00);
        Reservation reservation = Reservation.builder()
                .id(10L)
                .roomReservationList(List.of(new RoomReservation()))
                .build();
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(today);
            Mockito.when(reservationRepository.findById(10L)).thenReturn(Optional.ofNullable(reservation));
            checkInReservationUC.execute(reservation.getId());
            Mockito.verify(reservationRepository, Mockito.times(1)).save(reservation);
            Assertions.assertNotNull(reservation.getCheckIn());
        }
    }

    @Test
    @DisplayName("Should Thow when Reservation not found")
    public void shouldThrowCheckIn() {
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

}
