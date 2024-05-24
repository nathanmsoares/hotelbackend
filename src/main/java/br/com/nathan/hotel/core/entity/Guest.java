package br.com.nathan.hotel.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "guest_hotel")
@Data
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_hotel_seq")
    @SequenceGenerator(name = "guest_hotel_seq", sequenceName = "guest_hotel_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String telephone;

    @NotEmpty
    private String cpf;

    private final Instant createdTime = Instant.now();

    public Guest(Long id, String name, String telephone, String cpf) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.cpf = cpf;
    }

    public Guest() {
    }
}
