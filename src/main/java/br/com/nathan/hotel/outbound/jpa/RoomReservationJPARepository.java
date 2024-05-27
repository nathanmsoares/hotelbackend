package br.com.nathan.hotel.outbound.jpa;

import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomReservationJPARepository extends RoomReservationRepository, JpaRepository<RoomReservation, Long> {

    List<RoomReservation> findAllByPaidAndReservationCheckInIsNotNull(Boolean paid);

    default List<RoomReservation> saveAllRoomReservation(List<RoomReservation> roomReservationList) {
        return saveAll(roomReservationList);
    }

}
