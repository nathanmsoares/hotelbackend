package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class FindGuestNotCheckedInUCIT {

    @Autowired
    private FindGuestNotCheckedInUC findGuestNotCheckedInUC;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    public void tearDown() {
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("Should find Guests Not Checked in")
    public void shouldFind() {
        Guest guestFirst = guestRepository.save(Guest.builder().name("teste99").telephone("+5547999999999").cpf("10000000000").build());
        Guest guestSecond = guestRepository.save(Guest.builder().name("teste99").telephone("+5547999999992").cpf("20000000000").build());
        Reservation reservation = Reservation.builder()
                .guestList(Arrays.asList(guestFirst, guestSecond))
                .build();
        reservation = reservationRepository.save(reservation);
        Assertions.assertEquals(2, findGuestNotCheckedInUC.findGuests(guestFirst.getName()).size());
        Assertions.assertEquals(1, findGuestNotCheckedInUC.findGuests(guestFirst.getCpf()).size());
        Assertions.assertEquals(1, findGuestNotCheckedInUC.findGuests(guestSecond.getTelephone()).size());
    }

}
