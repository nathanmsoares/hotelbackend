package br.com.nathan.hotel.core.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoomUnitTest {

    @Test
    @DisplayName("Should set Taken to True")
    public void setTakenTrue() {
        Room room = Room.builder()
                .number(10)
                .floor(99)
                .build();
        Assertions.assertFalse(room.getTaken());
        room.takeRoom();
        Assertions.assertTrue(room.getTaken());
    }

    @Test
    @DisplayName("Should set Taken to False")
    public void setTakenFalse() {
        Room room = Room.builder()
                .number(10)
                .floor(99)
                .taken(Boolean.TRUE)
                .build();
        Assertions.assertTrue(room.getTaken());
        room.clearRoom();
        Assertions.assertFalse(room.getTaken());
    }
}
