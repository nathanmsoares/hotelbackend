package br.com.nathan.hotel.outbound.rest.jpa;

import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJPARepository extends ReservationRepository, JpaRepository<Reservation, Long> {
}
