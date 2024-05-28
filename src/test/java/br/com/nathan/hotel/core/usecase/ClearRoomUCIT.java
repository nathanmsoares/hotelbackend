package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
public class ClearRoomUCIT {

    @Autowired
    private ClearRoomUC clearRoomUC;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @DisplayName("Should Clear Room")
    public void shouldClear() {
        Room room = Room.builder()
                .number(10)
                .floor(99)
                .taken(Boolean.TRUE)
                .build();
        room = roomRepository.save(room);
        clearRoomUC.execute(room.getId());
        Assertions.assertFalse(roomRepository.findById(room.getId()).get().getTaken());
    }
}
