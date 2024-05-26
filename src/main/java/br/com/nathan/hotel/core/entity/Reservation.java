package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.exception.CheckInNotAllowedException;
import br.com.nathan.hotel.core.exception.RoomReservationEmptyException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    @SequenceGenerator(name = "reservation_seq", sequenceName = "reservation_seq", allocationSize = 1)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "reservation_guest",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    @NotEmpty
    private List<Guest> guestList;

    @OneToMany
    private List<Parking> parkingList;

    @OneToMany
    private List<RoomReservation> roomReservationList;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    @NotNull
    private final Instant createdTime = Instant.now();

    public void checkInHotel() {
        isCheckedIn();
        isRoomReservationPresent();
        LocalDateTime checkIn = LocalDateTime.now();
        log.info("Checking In on Reservation id {}", getId());
        isBefore14(checkIn);
        setCheckIn(checkIn);
        log.info("Checked In on Reservation id {} at {}", getId(), getCheckIn());
    }

    private void isCheckedIn() {
        if (Objects.nonNull(getCheckIn())) {
            throw new CheckInNotAllowedException("Check-In já realizado");
        }
    }

    private void isBefore14(LocalDateTime checkIn) {
        LocalDateTime todayAt14 = LocalDateTime.now().toLocalDate().atTime(LocalTime.of(14, 0));
        if (checkIn.isBefore(todayAt14)) {
            log.error("Tried to Check-In before 14:00 hours");
            throw new CheckInNotAllowedException("Antes das 14:00 não é possivel realizar o Check-In");
        }
    }

    private void isRoomReservationPresent() {
        log.info("Checking if Reservation id {} has any RoomReservation", getId());
        if (Objects.isNull(getRoomReservationList()) || getRoomReservationList().isEmpty()) {
            throw new RoomReservationEmptyException("Não há Reservas de quartos para a Reserva");
        }
    }

}
