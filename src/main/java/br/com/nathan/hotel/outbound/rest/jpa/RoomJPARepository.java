package br.com.nathan.hotel.outbound.rest.jpa;

import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomJPARepository extends RoomRepository, JpaRepository<Room, Long> {
}
