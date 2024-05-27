package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class SetRoomReservationExpenseUCUnitTest {

    private SetRoomReservationExpenseUC setRoomReservationExpenseUC;

    @Mock
    private RoomReservationRepository roomReservationRepository;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        setRoomReservationExpenseUC = new SetRoomReservationExpenseUC(roomReservationRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("should set Expenses")
    public void shouldSetExpenses() {
        RoomReservation roomReservationFirst = RoomReservation.builder().id(10L).build();
        RoomReservation roomReservationSecond = RoomReservation.builder().id(11L).build();
        Assertions.assertNull(roomReservationFirst.getExpense());
        Assertions.assertNull(roomReservationSecond.getExpense());
        setRoomReservationExpenseUC.execute(List.of(roomReservationFirst, roomReservationSecond));
        Assertions.assertNotNull(roomReservationFirst.getExpense());
        Assertions.assertNotNull(roomReservationSecond.getExpense());
        Mockito.verify(roomReservationRepository, Mockito.times(1)).saveAllRoomReservation(Mockito.anyList());
    }
}
