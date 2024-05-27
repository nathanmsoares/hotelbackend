package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class SetParkingExpenseUCUnitTest {

    private SetParkingExpenseUC setParkingExpenseUC;

    @Mock
    private ParkingRepository parkingRepository;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        setParkingExpenseUC = new SetParkingExpenseUC(parkingRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("should set Expenses")
    public void shouldSetExpenses() {
        Parking parkingFirst = Parking.builder().id(10L).build();
        Parking parkingSecond = Parking.builder().id(11L).build();
        Assertions.assertNull(parkingFirst.getExpense());
        Assertions.assertNull(parkingSecond.getExpense());
        setParkingExpenseUC.execute(List.of(parkingFirst, parkingSecond));
        Assertions.assertNotNull(parkingFirst.getExpense());
        Assertions.assertNotNull(parkingSecond.getExpense());
        Mockito.verify(parkingRepository, Mockito.times(1)).saveAllParking(Mockito.anyList());
    }

}
