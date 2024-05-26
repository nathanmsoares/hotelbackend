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
    private Room room;

    @ManyToOne
    @NotNull
    private Reservation reservation;

    private Double expense;

    @NotNull
    private final Instant createdTime = Instant.now();

    public void setExpenseFirstDay() {
        if(Objects.isNull(expense)) {
            if (isWeekend()) {
                log.info("Weekend day, setting expense to R$ {}", WEEKEND_EXPENSE);
                expense = WEEKEND_EXPENSE;
                return;
            }
            log.info("Week day, setting expense to R$ {}", WEEK_EXPENSE);
            expense = WEEK_EXPENSE;
        }
    }

    public boolean isWeekend() {
        Set<DayOfWeek> weekendDays = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        return weekendDays.contains(LocalDateTime.now().getDayOfWeek());
    }

}
