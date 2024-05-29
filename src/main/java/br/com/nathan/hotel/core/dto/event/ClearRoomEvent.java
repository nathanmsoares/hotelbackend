package br.com.nathan.hotel.core.dto.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClearRoomEvent extends ApplicationEvent {

    private final Long roomId;

    public ClearRoomEvent(Object source) {
        super(source);
        roomId = (Long) source;
    }
}
