package br.com.nathan.hotel.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

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
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "telephone")
    private String telephone;

    @NotEmpty
    @Column(name = "cpf")
    private String cpf;

    @ManyToMany(mappedBy = "guestList")
    @JsonIgnore
    private List<Reservation> reservationList;

    @NotNull
    private final Instant createdTime = Instant.now();

}
