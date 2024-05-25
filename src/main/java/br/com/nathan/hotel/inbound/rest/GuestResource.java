package br.com.nathan.hotel.inbound.rest;

import br.com.nathan.hotel.core.dto.EntityDTO;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.usecase.CreateGuestUC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/guest")
@RestController
public record GuestResource(CreateGuestUC createGuestUC) {

    @PostMapping
    public ResponseEntity<EntityDTO> createGuest(@RequestBody CreateGuestCommand command) {
        Guest guest = createGuestUC.execute(command);
        return new ResponseEntity<>(EntityDTO.of(guest.getId()), HttpStatus.CREATED);
    }
}
