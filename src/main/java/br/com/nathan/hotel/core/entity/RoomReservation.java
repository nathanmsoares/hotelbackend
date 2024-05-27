package br.com.nathan.hotel.core.entity;

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
@Table(name = "room_reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class RoomReservation {

    private static final double WEEKEND_EXPENSE = 180d;

    private static final double WEEK_EXPENSE = 120d;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_reservation_seq")
    @SequenceGenerator(name = "room_reservation_seq", sequenceName = "room_reservation_seq", allocationSize = 1)
    @Column(name = "room_reservation_id")
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "expense")
    private Double expense;

    @Builder.Default
    @Column(name = "paid")
    private Boolean paid = Boolean.FALSE;

    @NotNull
    @Column(name = "created_time")
    private final Instant createdTime = Instant.now();

    public void setExpenseFirstDay() {
        if (Objects.isNull(expense)) {
            setExpense(getDayPrice());
        }
    }

    public void addExpenseToTheDay() {
        setExpense(getExpense() + getDayPrice());
    }

    public void checkOut() {
        if (isAfterCheckOutHour()) {
            reducePrice();
        }
        setPaid(Boolean.TRUE);
    }

    private void reducePrice() {
        setExpense(getExpense() - (getDayPrice() / 2));
    }

    private Boolean isAfterCheckOutHour() {
        return LocalDateTime.now().getHour() > getReservation().getCheckOutHour();
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

}
