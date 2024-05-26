package br.com.nathan.hotel;

import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class HotelApplication implements CommandLineRunner {

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private RoomRepository roomRepository;

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

//	@PostConstruct
//	void started() {
//		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
//	}

	@Override
	public void run(String... args) throws Exception {
		Room room = Room.builder()
				.floor(1)
				.number(10)
				.build();
		roomRepository.save(room);
//		CreateGuestCommand guestCommand = new CreateGuestCommand("fulano", "100", "100-100");
//		Guest guest = guestCommand.toEntity();
//		guestRepository.save(guest);
//		System.out.println("parar");
	}
}
