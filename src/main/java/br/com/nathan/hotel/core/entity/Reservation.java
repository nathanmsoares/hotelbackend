package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.dto.ReservationDTO;
import br.com.nathan.hotel.core.dto.event.CheckInParkingExpenseEvent;
import br.com.nathan.hotel.core.dto.event.CheckInRoomReservationExpenseEvent;
import br.com.nathan.hotel.core.dto.event.CheckOutReservationEvent;
import br.com.nathan.hotel.core.exception.CheckInNotAllowedException;
import br.com.nathan.hotel.core.exception.RoomReservationEmptyException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
@EqualsAndHashCode(callSuper=false)
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
    @Getter(onMethod_ = @JsonIgnore)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Guest> guestList;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "reservation")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Parking parking;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "reservation")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RoomReservation roomReservation;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "totalCost")
    private Double totalCost;

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

        sendCheckInRoomReservationExpenseEvent();
        sendCheckInParkingExpenseEvent();

        setCheckIn(checkIn);
        log.info("Checked In on Reservation id {} at {}", getId(), getCheckIn());
    }

    public void checkOut() {
        if (Objects.isNull(getCheckOut())) {
            registerEvent(new CheckOutReservationEvent(getId()));
            setCheckOut(LocalDateTime.now());
        }
    }

    public void setTotalCostAfterCheckOut() {
        if (Objects.nonNull(getCheckOut())) {
            setTotalCost(generateTotalCost());
        }
    }

    public Double getTotalCost() {
        return totalCost;
    }

    private Double generateTotalCost() {
        return getParking().getExpense() + roomReservation.getExpense();
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
        if (Objects.isNull(getRoomReservation())) {
            throw new RoomReservationEmptyException("Não há Reservas de quartos para a Reserva");
        }
    }

    private void sendCheckInRoomReservationExpenseEvent() {
        registerEvent(new CheckInRoomReservationExpenseEvent(getRoomReservation()));
    }

    private void sendCheckInParkingExpenseEvent() {
        if (Objects.nonNull(getParking())) {
            registerEvent(new CheckInParkingExpenseEvent(getParking()));
        }
    }

    public ReservationDTO toDTO() {
        return ReservationDTO.builder()
                .id(getId())
                .totalCost(getTotalCost())
                .checkOut(getCheckOut())
                .checkIn(getCheckIn())
                .roomReservation(getRoomReservation().toDTO())
                .parking(getParking().toDTO())
                .guestList(getGuestList().stream().map(Guest::toDTO).toList())
                .checkInHour(getCheckInHour())
                .checkOutHour(getCheckOutHour())
                .createdTime(getCreatedTime())
                .build();
    }

}
