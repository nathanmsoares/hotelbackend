package br.com.nathan.hotel.outbound.jpa;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationJPARepository extends ReservationRepository, JpaRepository<Reservation, Long> {

    default Reservation saveEntity(Reservation reservation) {
        return saveAll(List.of(reservation)).get(0);
    }
}
