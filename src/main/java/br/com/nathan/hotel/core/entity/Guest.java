package br.com.nathan.hotel.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "guest_hotel")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_hotel_seq")
    @SequenceGenerator(name = "guest_hotel_seq", sequenceName = "guest_hotel_seq", allocationSize = 1)
    @Column(name = "guest_id")
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String telephone;

    @NotEmpty
    private String cpf;

    private final Instant createdTime = Instant.now();

}
