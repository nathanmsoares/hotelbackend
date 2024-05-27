package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.dto.GuestDTO;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FindGuestGenericUC {

    private final GuestRepository guestRepository;

    public List<GuestDTO> findGuests(String field) {
        log.info("Finding Guests by generic info");
        return guestRepository.findAllByNameOrTelephoneOrCpf(field, field, field).stream().map(
                Guest::toDTO
        ).toList();
    }
}
