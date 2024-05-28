package br.com.nathan.hotel.inbound.rest;

import br.com.nathan.hotel.core.dto.EntityDTO;
import br.com.nathan.hotel.core.dto.GenericFieldDTO;
import br.com.nathan.hotel.core.dto.GuestDTO;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.usecase.CreateGuestUC;
import br.com.nathan.hotel.core.usecase.FindGuestGenericUC;
import br.com.nathan.hotel.core.usecase.FindGuestInHotelUC;
import br.com.nathan.hotel.core.usecase.FindGuestNotCheckedInUC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/guest")
@RestController
public record GuestController(CreateGuestUC createGuestUC,
                              FindGuestGenericUC findGuestGenericUC,
                              FindGuestInHotelUC findGuestInHotelUC,
                              FindGuestNotCheckedInUC findGuestNotCheckedInUC) {

    @PostMapping
    public ResponseEntity<EntityDTO> createGuest(@RequestBody CreateGuestCommand command) {
        Guest guest = createGuestUC.execute(command);
        return new ResponseEntity<>(EntityDTO.of(guest.getId()), HttpStatus.CREATED);
    }

    @PostMapping("/generic/")
    public ResponseEntity<List<GuestDTO>> findGenericGuests(@RequestBody GenericFieldDTO genericFieldDTO) {
        return new ResponseEntity<>(findGuestGenericUC.findGuests(genericFieldDTO.getField()), HttpStatus.OK);
    }

    @PostMapping("/inHotel/")
    public ResponseEntity<List<GuestDTO>> findGuestsInHotel(@RequestBody GenericFieldDTO genericFieldDTO) {
        return new ResponseEntity<>(findGuestInHotelUC.findGuests(genericFieldDTO.getField()), HttpStatus.OK);
    }

    @PostMapping("/notCheckedIn/")
    public ResponseEntity<List<GuestDTO>> findGuestsNotCheckedIn(@RequestBody GenericFieldDTO genericFieldDTO) {
        return new ResponseEntity<>(findGuestNotCheckedInUC.findGuests(genericFieldDTO.getField()), HttpStatus.OK);
    }

}
