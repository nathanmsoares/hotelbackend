package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateReservationCommand {

    @NotNull
    private Room room;

    @NotEmpty
    private List<Guest> guestList;

    public Reservation toEntity() {
        return Reservation.builder()
                .room(room)
                .guestList(guestList)
                .build();
    }
}
