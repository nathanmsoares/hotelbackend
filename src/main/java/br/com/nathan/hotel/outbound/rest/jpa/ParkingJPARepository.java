package br.com.nathan.hotel.outbound.rest.jpa;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingJPARepository extends ParkingRepository, JpaRepository<Parking, Long> {
}
