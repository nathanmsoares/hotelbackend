package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Parking;

import java.util.Optional;

public interface ParkingRepository {

    Parking save(Parking parking);

    void deleteAll();

    Optional<Parking> findById(Long id);

}
