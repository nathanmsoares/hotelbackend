package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.RoomReservation;
import br.com.nathan.hotel.core.repository.RoomReservationRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        RoomReservation roomReservation = RoomReservation.builder().id(10L).build();
        Assertions.assertNull(roomReservation.getExpense());
        setRoomReservationExpenseUC.execute(roomReservation);
        Assertions.assertNotNull(roomReservation.getExpense());
        Mockito.verify(roomReservationRepository, Mockito.times(1)).save(Mockito.any());
    }
}
