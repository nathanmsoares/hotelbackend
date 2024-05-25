package br.com.nathan.hotel;

import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestHotelApplication.class)
public class TestCreateGuestTest {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void test() {
        Guest guest = (new CreateGuestCommand("teste", "teste", "teste")).toEntity();
        guestRepository.saveEntity(guest);
        entityManager.find(Guest.class, guest.getId());
    }

}
