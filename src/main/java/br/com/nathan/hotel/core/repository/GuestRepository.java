package br.com.nathan.hotel.core.repository;

import br.com.nathan.hotel.core.entity.Guest;

public interface GuestRepository {

    Guest saveEntity(Guest guest);
}
