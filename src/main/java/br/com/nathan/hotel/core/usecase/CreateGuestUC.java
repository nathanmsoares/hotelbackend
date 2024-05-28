package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateGuestUC {

    private final GuestRepository guestRepository;

    @Transactional
    public Guest execute(CreateGuestCommand command) {
        Guest guest = guestRepository.save(command.toEntity());
        log.info("Saved Guest id {}", guest.getId());
        return guest;
    }

}
