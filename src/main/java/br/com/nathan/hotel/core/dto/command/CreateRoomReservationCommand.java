package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.entity.RoomReservation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public class CreateRoomReservationCommand {

    @NotNull
    private Reservation reservation;

    @NotNull
    private Room room;

    public RoomReservation toEntity() {
        log.info("Creating Room Reservation on room id {} and on reservation id {}", room.getId(), reservation.getId());
        RoomReservation roomReservation = RoomReservation.builder()
                .reservation(reservation)
                .room(room)
                .build();
        return roomReservation;
    }
}
