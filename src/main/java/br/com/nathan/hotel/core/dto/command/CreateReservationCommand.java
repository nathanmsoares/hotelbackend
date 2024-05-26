package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@AllArgsConstructor
@Slf4j
public class CreateReservationCommand {

    @NotEmpty
    private List<Guest> guestList;

    public Reservation toEntity() {
        log.info("Creating Reservation on Guest Ids {}", guestList.stream().map(Guest::getId).toList());
        return Reservation.builder()
                .guestList(guestList)
                .build();
    }
}
