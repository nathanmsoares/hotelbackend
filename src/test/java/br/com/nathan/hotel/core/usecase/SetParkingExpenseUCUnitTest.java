package br.com.nathan.hotel.core.usecase;

import br.com.nathan.hotel.core.entity.Parking;
import br.com.nathan.hotel.core.repository.ParkingRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        Parking parking = Parking.builder().id(10L).build();
        Assertions.assertNull(parking.getExpense());
        setParkingExpenseUC.execute(parking);
        Assertions.assertNotNull(parking.getExpense());
        Mockito.verify(parkingRepository, Mockito.times(1)).save(Mockito.any());
    }

}
