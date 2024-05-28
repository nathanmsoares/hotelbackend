package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.dto.GuestDTO;
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
    private List<GuestDTO> guestListDTO;

    @NotNull
    private Boolean parkingRequested;

    @NotNull
    private Room room;

    public Reservation toEntity() {
        log.info("Creating Reservation on Guest Ids {}", guestListDTO.stream().map(Guest::getId).toList());
        return Reservation.builder()
                .guestList(guestListDTO.stream().map(GuestDTO::toEntity).toList())
                .build();
    }
}
