package br.com.nathan.hotel.core.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EntityDTO {

    private Long id;

    public static EntityDTO of(Long id) {
        return EntityDTO.builder().id(id).build();
    }

}
