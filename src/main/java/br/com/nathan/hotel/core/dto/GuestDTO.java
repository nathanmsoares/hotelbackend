package br.com.nathan.hotel.core.dto;

import br.com.nathan.hotel.core.entity.Guest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class GuestDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String telephone;

    @NotEmpty
    private String cpf;

    private Instant createdTime;

    public Guest toEntity() {
        return Guest.builder()
                .id(id)
                .name(name)
                .telephone(telephone)
                .cpf(cpf)
                .build();
    }

}
