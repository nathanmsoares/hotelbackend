package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CheckOutReservationUC {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void execute() {

    }
}
