package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.EntityDTO;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateGuestUC {

    private final GuestRepository guestRepository;

    public Guest execute(CreateGuestCommand command) {
        return guestRepository.saveEntity(command.toEntity());
    }

}
