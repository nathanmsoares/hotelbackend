package br.com.nathan.hotel.core.dto.command;

import br.com.nathan.hotel.core.entity.Guest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public class CreateGuestCommand {

    @NotEmpty
    private String name;

    @NotEmpty
    private String telephone;

    @NotEmpty
    private String cpf;

    public Guest toEntity() {
        log.info("Creating Guest");
        return Guest.builder()
                .name(name)
                .telephone(telephone)
                .cpf(cpf)
                .build();
    }

}
