package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.exception.ParkingNotFoundException;
import br.com.nathan.hotel.core.exception.ReservationNotFoundException;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CheckOutParkingUC {

    private final ParkingRepository parkingRepository;

    public void execute(Long parkingId) {
        Optional<Parking> reservationOptional = parkingRepository.findById(parkingId);
        Parking parking =
                reservationOptional.orElseThrow(() -> new ParkingNotFoundException("Estacionamento não encontrado"));
        parking.checkOut();
        parkingRepository.save(parking);
    }

}
