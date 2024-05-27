package br.com.nathan.hotel.outbound.jpa;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingJPARepository extends ParkingRepository, JpaRepository<Parking, Long> {
    default List<Parking> saveAllParking(List<Parking> parkingList) {
        return saveAll(parkingList);
    }
}
