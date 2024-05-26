package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Guest;

import java.util.Optional;

public interface GuestRepository {

    Guest save(Guest guest);

    Guest saveAndFlush(Guest guest);

//    List<Guest> findByNameOrTelephoneOrCPFAnd

    void deleteAll();

    Optional<Guest> findById(Long id);
}
