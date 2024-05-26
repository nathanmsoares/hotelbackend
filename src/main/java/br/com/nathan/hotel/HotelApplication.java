package br.com.nathan.hotel;

import br.com.nathan.hotel.core.dto.command.CreateGuestCommand;
import br.com.nathan.hotel.core.dto.command.CreateReservationCommand;
import br.com.nathan.hotel.core.dto.command.CreateRoomReservationCommand;
import br.com.nathan.hotel.core.entity.Guest;
import br.com.nathan.hotel.core.entity.Reservation;
import br.com.nathan.hotel.core.entity.Room;
import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.GuestRepository;
import br.com.nathan.hotel.core.repository.ReservationRepository;
import br.com.nathan.hotel.core.repository.RoomRepository;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import br.com.nathan.hotel.core.usecase.CreateReservationUC;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
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

	@Autowired
	private CreateReservationUC createReservationUC;

	@Autowired
	private RoomReservationRepository roomReservationRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Room room = Room.builder()
				.floor(1)
				.number(10)
				.build();
		room = roomRepository.saveAndFlush(room);

		CreateGuestCommand createGuestCommand =
				new CreateGuestCommand("name", "+554799999999", "100999999-20");
		Guest guest = guestRepository.saveAndFlush(createGuestCommand.toEntity());
		CreateReservationCommand createReservationCommand = new CreateReservationCommand(Arrays.asList(guest));

		Reservation reservation = reservationRepository.saveAndFlush(createReservationCommand.toEntity());

//		Room room = Room.builder()
//				.number(10)
//				.floor(99)
//				.build();
//		room = roomRepository.save(room);

		CreateRoomReservationCommand createRoomReservationCommand = new CreateRoomReservationCommand(reservation, room);
		RoomReservation roomReservation = roomReservationRepository.saveAndFlush(createRoomReservationCommand.toEntity());
		reservation.setRoomReservationList(Arrays.asList(roomReservation));
//		reservationRepository.saveAndFlush(reservation);
		System.out.println("parar");


//		CreateGuestCommand guestCommand = new CreateGuestCommand("fulano", "100", "100-100");
//		Guest guest = guestCommand.toEntity();
//		guestRepository.save(guest);
//		System.out.println("parar");
	}
}
