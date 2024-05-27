package br.com.nathan.hotel.outbound.jpa;

import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestJPARepository extends GuestRepository, JpaRepository<Guest, Long> {

}
