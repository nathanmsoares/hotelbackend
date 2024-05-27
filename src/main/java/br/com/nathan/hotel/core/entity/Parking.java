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
        if (Objects.isNull(getExpense())) {
            setExpense(getDayPrice());
        }
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

}
