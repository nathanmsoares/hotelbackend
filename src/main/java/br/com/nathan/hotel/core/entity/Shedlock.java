package br.com.nathan.hotel.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shedlock")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shedlock {

    @Id
    @Column(name = "name")
    @Size(max = 64)
    private String name;

    @Column(name = "lock_until")
    private LocalDateTime lockUntil;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "locked_by")
    @Size(max = 255)
    private String lockedBy;

}
