package br.com.nathan.hotel;

import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.RoomRepository;
import jakarta.transaction.Transactional;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@SpringBootApplication
public class HotelApplication implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Room room = Room.builder()
                .floor(1)
                .number(10)
                .build();
        room = roomRepository.saveAndFlush(room);
    }
}
