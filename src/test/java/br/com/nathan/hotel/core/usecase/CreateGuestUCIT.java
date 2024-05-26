package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class CreateGuestUCIT {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private CreateGuestUC createGuestUC;

    @AfterEach
    public void tearDown() {
        guestRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a Guest")
    @Transactional
    public void createGuest() {
        CreateGuestCommand createGuestCommand =
                new CreateGuestCommand("name", "+554799999999", "100999999-20");
        Guest guest = createGuestUC.execute(createGuestCommand);

        Optional<Guest> guestOptional = guestRepository.findById(guest.getId());
        Assertions.assertTrue(guestOptional.isPresent());

        guest = guestOptional.get();
        Assertions.assertEquals(createGuestCommand.getName(), guest.getName());
        Assertions.assertEquals(createGuestCommand.getCpf(), guest.getCpf());
        Assertions.assertEquals(createGuestCommand.getTelephone(), guest.getTelephone());
    }

}
