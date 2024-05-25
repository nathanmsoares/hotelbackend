package br.com.nathan.hotel.core.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class AbstractEntity {

    private Instant createdTime = Instant.now();
}
