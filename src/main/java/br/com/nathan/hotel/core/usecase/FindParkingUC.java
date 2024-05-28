package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.ParkingDTO;
import br.com.nathan.hotel.core.entity.Parking;
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
public class FindParkingUC {

    private final ParkingRepository repository;

    public ParkingDTO findById(Long id) {
        log.info("Seaching for Parking id {}", id);
        Optional<Parking> parkingOptional = repository.findById(id);
        Parking parking =
                parkingOptional.orElseThrow(() -> new ParkingNotFoundException("Estacionamento não encontrado"));
        return parking.toDTO();
    }
}
