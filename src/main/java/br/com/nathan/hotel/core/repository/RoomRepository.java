package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Room;

public interface RoomRepository {

    Room save(Room room);

    Room saveAndFlush(Room room);
}
