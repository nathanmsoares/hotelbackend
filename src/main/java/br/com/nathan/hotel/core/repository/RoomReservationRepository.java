package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.RoomReservation;

import java.util.List;
import java.util.Optional;

public interface RoomReservationRepository {

    RoomReservation save(RoomReservation roomReservation);

    RoomReservation saveAndFlush(RoomReservation roomReservation);

    void deleteAll();

    Optional<RoomReservation> findById(Long id);

    List<RoomReservation> findAllByClosedAndReservationCheckInIsNotNull(Boolean closed);

    List<RoomReservation> saveAllRoomReservation(List<RoomReservation> roomReservationList);

    List<RoomReservation> findAllByReservationId(Long id);

    List<RoomReservation> findAll();

}
