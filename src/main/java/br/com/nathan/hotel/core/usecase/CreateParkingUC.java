package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.command.CreateParkingCommand;
import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateParkingUC {

    private final ParkingRepository parkingRepository;

    public Parking execute(CreateParkingCommand command) {
        Parking parking = parkingRepository.save(command.toEntity());
        log.info("Saved Parking Id {}, on Reservation Id {}", parking.getId(), parking.getReservation().getId());
        return parking;
    }
}
