package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class CheckInReservationUCIT {

    @Test
    @DisplayName("Should checkIn")
    @Transactional
    public void shouldCheckIn() {

    }
}
