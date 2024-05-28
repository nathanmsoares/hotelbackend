package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.exception.ReservationNotFoundException;
import br.com.nathan.hotel.core.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class SetRoomTakenUC {

    private final RoomRepository roomRepository;

    public void execute(Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Room room = roomOptional.orElseThrow(() -> new ReservationNotFoundException("Room não encontrado"));
        room.takeRoom();
        roomRepository.save(room);
    }
}
