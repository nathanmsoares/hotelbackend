package br.com.nathan.hotel.core.dto.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClearRoomTaken extends ApplicationEvent {

    private final Long roomId;

    public ClearRoomTaken(Object source) {
        super(source);
        this.roomId = (Long) source;
    }

}
