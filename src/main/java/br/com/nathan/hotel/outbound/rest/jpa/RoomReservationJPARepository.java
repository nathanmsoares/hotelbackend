package br.com.nathan.hotel.outbound.rest.jpa;

import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomReservationJPARepository extends RoomReservationRepository, JpaRepository<RoomReservation, Long> {

}
