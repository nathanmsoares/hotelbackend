package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.dto.ParkingDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "parking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class Parking {

    private static final double WEEKEND_EXPENSE = 20d;

    private static final double WEEK_EXPENSE = 15d;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_seq")
    @SequenceGenerator(name = "parking_seq", sequenceName = "parking_seq", allocationSize = 1)
    @Column(name = "parking_id")
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "expense")
    private Double expense;

    @Builder.Default
    @Column(name = "closed")
    private Boolean closed = Boolean.FALSE;

    @NotNull
    @Column(name = "created_time")
    private final Instant createdTime = Instant.now();

    @Column(name = "check_out_hour")
    @NotNull
    private final Integer checkOutHour = 12;

    @Column(name = "check_in_hour")
    @NotNull
    private final Integer checkInHour = 14;

    public void setExpenseFirstDay() {
        if (Objects.isNull(getExpense())) {
            log.info("Setting First day Expense");
            setExpense(getDayPrice());
        }
    }

    public void checkOut() {
        if (!isCheckInCheckOutSameDay() && isAfterCheckOutHour()) {
            log.info("Checking out on id {}", getId());
            reducePrice();
        }
        setClosed(Boolean.TRUE);
    }

    private Boolean isCheckInCheckOutSameDay() {
        return LocalDateTime.now().toLocalDate().equals(getReservation().getCheckIn().toLocalDate());
    }

    private Boolean isAfterCheckOutHour() {
        return LocalDateTime.now().getHour() > getReservation().getCheckOutHour();
    }

    private void reducePrice() {
        setExpense(getExpense() - (getDayPrice() / 2));
    }

    private Double getDayPrice() {
        Set<DayOfWeek> weekend = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        if (weekend.contains(LocalDateTime.now().getDayOfWeek())) {
            log.info("Weekend day, setting expense to R$ {}", WEEKEND_EXPENSE);
            return WEEKEND_EXPENSE;
        }
        log.info("Week day, setting expense to R$ {}", WEEK_EXPENSE);
        return WEEK_EXPENSE;
    }

    public void addExpenseToTheDay() {
        setExpense(getExpense() + getDayPrice());
    }

    public ParkingDTO toDTO() {
        return ParkingDTO.builder()
                .id(getId())
                .reservation(getReservation().toDTO())
                .closed(getClosed())
                .createdTime(getCreatedTime())
                .expense(getExpense())
                .build();
    }

}
