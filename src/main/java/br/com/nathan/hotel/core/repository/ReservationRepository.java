package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation saveEntity(Reservation reservation);

    Reservation saveAndFlush(Reservation reservation);

    Optional<Reservation> findById(Long id);

    void deleteAll();
}
