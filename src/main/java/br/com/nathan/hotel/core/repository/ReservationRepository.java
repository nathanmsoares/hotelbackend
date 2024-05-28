package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    Reservation saveEntity(Reservation reservation);

    Reservation saveAndFlush(Reservation reservation);

    Optional<Reservation> findById(Long id);

    void deleteAll();
}
