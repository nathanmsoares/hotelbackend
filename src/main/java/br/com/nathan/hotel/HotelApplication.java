package br.com.nathan.hotel;

import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelApplication implements CommandLineRunner {

	@Autowired
	private GuestRepository guestRepository;

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CreateGuestCommand guestCommand = new CreateGuestCommand("fulano", "100", "100-100");
		Guest guest = guestCommand.toEntity();
		guestRepository.saveEntity(guest);
		System.out.println("parar");
	}
}
