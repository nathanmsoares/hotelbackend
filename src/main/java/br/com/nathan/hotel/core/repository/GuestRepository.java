package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestRepository {

    Guest save(Guest guest);

    Guest saveAndFlush(Guest guest);

    List<Guest> findAllByNameContainingOrTelephoneContainingOrCpfContaining(String name, String telephone, String cpf);

    List<Guest> findAllNotCheckIn(String field);

    void deleteAll();

    Optional<Guest> findById(Long id);

    List<Guest> findAllGuestsByFieldAndActiveReservation(String field);



}
