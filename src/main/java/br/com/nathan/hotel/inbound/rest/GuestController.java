package br.com.nathan.hotel.inbound.rest;

import br.com.nathan.hotel.core.dto.EntityDTO;
import br.com.nathan.hotel.core.dto.GuestDTO;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.usecase.CreateGuestUC;
import br.com.nathan.hotel.core.usecase.FindGuestGenericUC;
import br.com.nathan.hotel.core.usecase.FindGuestInHotelUC;
import br.com.nathan.hotel.core.usecase.FindGuestNotCheckedInUC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/generic/{field}")
    public ResponseEntity<List<GuestDTO>> findGenericGuests(@RequestParam(value = "field") String field) {
        return new ResponseEntity<>(findGuestGenericUC.findGuests(field), HttpStatus.OK);
    }

    @GetMapping("/inHotel/{field}")
    public ResponseEntity<List<GuestDTO>> findGuestsInHotel(@RequestParam(value = "field") String field) {
        return new ResponseEntity<>(findGuestInHotelUC.findGuests(field), HttpStatus.OK);
    }

    @GetMapping("/notCheckedIn/{field}")
    public ResponseEntity<List<GuestDTO>> findGuestsNotCheckedIn(@RequestParam(value = "field") String field) {
        return new ResponseEntity<>(findGuestNotCheckedInUC.findGuests(field), HttpStatus.OK);
    }

}
