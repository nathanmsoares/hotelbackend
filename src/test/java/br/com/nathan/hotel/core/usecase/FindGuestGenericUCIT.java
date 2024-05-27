package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class FindGuestGenericUCIT {

    @Autowired
    private FindGuestGenericUC findGuestGenericUC;

    @Autowired
    private GuestRepository guestRepository;

    @AfterEach
    public void tearDown() {
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("Should find generic Guests")
    public void shouldFind() {
        Guest guestFirst = guestRepository.save(Guest.builder().name("teste99").telephone("+5547999999999").cpf("10000000000").build());
        Guest guestSecond = guestRepository.save(Guest.builder().name("teste99").telephone("+5547999999992").cpf("20000000000").build());
        Assertions.assertEquals(2, findGuestGenericUC.findGuests(guestFirst.getName()).size());
        Assertions.assertEquals(1, findGuestGenericUC.findGuests(guestFirst.getCpf()).size());
        Assertions.assertEquals(1, findGuestGenericUC.findGuests(guestSecond.getTelephone()).size());
        Assertions.assertEquals(1, findGuestGenericUC.findGuests("20").size());
        Assertions.assertEquals(2, findGuestGenericUC.findGuests("99").size());
        Assertions.assertEquals(1, findGuestGenericUC.findGuests("92").size());
    }

}
