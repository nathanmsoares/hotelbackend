package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.config.TestHotelConfiguration;
import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import br.com.nathan.hotel.core.repository.RoomRepository;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.List;
import java.util.TimeZone;

@SpringBootTest
@ContextConfiguration(classes = TestHotelConfiguration.class)
@Slf4j
public class CheckInReservationUCIT {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private CreateReservationUC createReservationUC;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CheckInReservationUC checkInReservationUC;

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    private final TimeZone timeZone = TimeZone.getDefault();

    @AfterEach
    public void tearDown() {
        roomReservationRepository.deleteAll();
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
        System.setProperty("user.timezone", timeZone.getID());
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone.getID()));
    }

    @Test
    @DisplayName("Should checkIn")
    @Transactional
    public void shouldCheckIn() {
        setZoneTimeAfter14Hour();
        CreateGuestCommand createGuestCommand =
                new CreateGuestCommand("name", "+554799999999", "100999999-20");
        Guest guest = guestRepository.save(createGuestCommand.toEntity());
        CreateReservationCommand createReservationCommand = new CreateReservationCommand(List.of(guest));
        Reservation reservation = createReservationUC.execute(createReservationCommand);

        Room room = Room.builder()
                .number(10)
                .floor(99)
                .build();
        room = roomRepository.save(room);

        CreateRoomReservationCommand createRoomReservationCommand = new CreateRoomReservationCommand(reservation, room);
        RoomReservation roomReservation = roomReservationRepository.save(createRoomReservationCommand.toEntity());
        reservation.setRoomReservationList(List.of(roomReservation));
        checkInReservationUC.execute(reservation.getId());
    }

    private String getTimeZoneId(Integer hourToCompare, LocalDateTime now) {
        for (String availableID : TimeZone.getAvailableIDs()) {
            try {
                LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(availableID));
                if (Integer.valueOf(hourToCompare).equals(localDateTime.getHour())) {
                    return availableID;
                }
            } catch (ZoneRulesException zoneRulesException) {
                log.error("Timezone not found: {}", zoneRulesException.getMessage());
            }
        }
        throw new RuntimeException("None TimeZone id found");
    }

    private void setZoneTimeAfter14Hour() {
        LocalDateTime now = LocalDateTime.now();
        if (isBefore14Hour(now)) {
            String zone = getTimeZoneId(15, now);
            System.setProperty("user.timezone", zone);
            TimeZone.setDefault(TimeZone.getTimeZone(zone));
        }
    }

    private Boolean isBefore14Hour(LocalDateTime now) {
        return now.isBefore(LocalDateTime.now().toLocalDate().atTime(LocalTime.of(14, 0)));
    }
}
