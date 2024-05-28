package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Room;

import java.util.Optional;

public interface RoomRepository {

    Room save(Room room);

    Room saveAndFlush(Room room);

    void deleteAll();

    Optional<Room> findById(Long id);

//    List<Room> findAllFree(LocalDateTime);

}
