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
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class Reservation extends AbstractAggregateRoot<Reservation> {

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

    @OneToMany(mappedBy = "reservation")
    private List<Parking> parkingList;

    @OneToMany(mappedBy = "reservation")
    private List<RoomReservation> roomReservationList;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "check_out_hour")
    @NotNull
    private final Integer checkOutHour = 12;

    @Column(name = "check_in_hour")
    @NotNull
    private final Integer checkInHour = 14;

    @Column(name = "created_time")
    @NotNull
    private final Instant createdTime = Instant.now();

    public Collection<Object> domainEvents() {
        return super.domainEvents();
    }

    public void checkInHotel() {
        isCheckedIn();
        isRoomReservationPresent();
        LocalDateTime checkIn = LocalDateTime.now();
        log.info("Checking In on Reservation id {}", getId());
        isCheckInAllowed(checkIn);
        setCheckIn(checkIn);
        log.info("Checked In on Reservation id {} at {}", getId(), getCheckIn());
    }

    public void checkOut() {

    }

    private void isCheckedIn() {
        if (Objects.nonNull(getCheckIn())) {
            throw new CheckInNotAllowedException("Check-In já realizado");
        }
    }

    private void isCheckInAllowed(LocalDateTime checkIn) {
        if (checkIn.getHour() < getCheckInHour()) {
            log.error("Tried to Check-In before {}:00 hours", getCheckInHour());
            throw new CheckInNotAllowedException("Não é possivel realizar o Check-In neste horário");
        }
    }

    private void isRoomReservationPresent() {
        log.info("Checking if Reservation id {} has any RoomReservation", getId());
        if (Objects.isNull(getRoomReservationList()) || getRoomReservationList().isEmpty()) {
            throw new RoomReservationEmptyException("Não há Reservas de quartos para a Reserva");
        }
    }

}
