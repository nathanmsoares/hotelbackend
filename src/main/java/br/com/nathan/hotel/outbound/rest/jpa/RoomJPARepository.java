package br.com.nathan.hotel.outbound.rest.jpa;

import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomJPARepository extends RoomRepository, JpaRepository<Room, Long> {
}
