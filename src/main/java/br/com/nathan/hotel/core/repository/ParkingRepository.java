package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Parking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository {

    Parking save(Parking parking);

    void deleteAll();

    Optional<Parking> findById(Long id);

    List<Parking> findAllByPaid(Boolean paid);

    List<Parking> saveAll(Iterable<Parking> entities);
}
