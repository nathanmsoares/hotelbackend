package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Guest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateGuestCommandUnitTest {

    @Test
    @DisplayName("Should create Guest")
    public void createGuest() {
        CreateGuestCommand command = new CreateGuestCommand("name", "+554799999999", "10099988812");
        Guest guest = command.toEntity();
        Assertions.assertEquals(command.getName(), guest.getName());
        Assertions.assertEquals(command.getTelephone(), guest.getTelephone());
        Assertions.assertEquals(command.getCpf(), guest.getCpf());
    }
}
