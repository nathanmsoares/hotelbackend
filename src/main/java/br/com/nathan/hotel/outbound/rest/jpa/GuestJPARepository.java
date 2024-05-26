package br.com.nathan.hotel.outbound.rest.jpa;

import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestJPARepository extends GuestRepository, JpaRepository<Guest, Long> {

}
