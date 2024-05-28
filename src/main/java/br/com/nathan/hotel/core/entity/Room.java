package br.com.nathan.hotel.core.entity;

import br.com.nathan.hotel.core.dto.RoomDTO;
import br.com.nathan.hotel.core.exception.RoomTakenException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(name = "room_seq", sequenceName = "room_seq", allocationSize = 1)
    @Column(name = "room_id")
    private Long id;

    @NotNull
    @Positive
    @Column(name = "floor")
    private Integer floor;

    @NotNull
    @Positive
    @Column(name = "number")
    private Integer number;

    @OneToMany(mappedBy = "room")
    private List<RoomReservation> roomReservationList;

    @Column(name = "taken")
    @Builder.Default
    private Boolean taken = Boolean.FALSE;

    @NotNull
    @Column(name = "created_time")
    private final Instant createdTime = Instant.now();

    public void takeRoom() {
        if (getTaken()) {
            throw new RoomTakenException("Quarto já alugado");
        }
        setTaken(Boolean.TRUE);
    }

    public void clearRoom() {
        setTaken(Boolean.FALSE);
    }

    public RoomDTO toDTO() {
        return RoomDTO.builder()
                .roomReservationList(roomReservationList.stream().map(RoomReservation::toDTO).toList())
                .number(getNumber())
                .floor(getFloor())
                .id(getId())
                .createdTime(getCreatedTime())
                .build();
    }

}
