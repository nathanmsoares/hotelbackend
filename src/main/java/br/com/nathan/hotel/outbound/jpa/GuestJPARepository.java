package br.com.nathan.hotel.outbound.jpa;

import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestJPARepository extends GuestRepository, JpaRepository<Guest, Long> {

    @Query("SELECT DISTINCT g FROM Guest g JOIN g.reservationList r " +
            "WHERE (g.name LIKE %:field% OR g.telephone LIKE %:field% OR g.cpf LIKE %:field%) " +
            "AND r.checkOut IS NULL AND r.checkIn IS NOT NULL")
    List<Guest> findAllGuestsByFieldAndActiveReservation(@Param("field") String field);


    @Query("SELECT DISTINCT g FROM Guest g JOIN g.reservationList r " +
            "WHERE (g.name LIKE %:field% OR g.telephone LIKE %:field% OR g.cpf LIKE %:field%) " +
            "AND r.checkIn IS NULL")
    List<Guest> findAllNotCheckIn(@Param("field") String field);

}
