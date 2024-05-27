package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class SetParkingExpenseUC {

    private final ParkingRepository parkingRepository;

    public void execute(List<Parking> parkingList) {
        parkingList.forEach(Parking::setExpenseFirstDay);
        parkingRepository.saveAllParking(parkingList);
    }

}
