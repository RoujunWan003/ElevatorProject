package elevatorMaster;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class ElevatorOrderTest {
	
	ElevatorOrder elevatorOrder ;

	@BeforeEach
	void setup() {

		elevatorOrder = new ElevatorOrder(2, 12, 5);

	}

	@Test
	public void test_getFloor() {
		
		assertEquals(2, elevatorOrder.getFloor());
	}
	
	@Test
	public void test_setFloor() {
		elevatorOrder.setFloor(10);
		assertEquals(10, elevatorOrder.getFloor());
	}
	@Test
	public void get_newFloor() {
		assertEquals(12, elevatorOrder.getNewFloor());
	}
	@Test
	public void test_setNewFloor() {
		
		elevatorOrder.setNewFloor(5);
		assertEquals(5, elevatorOrder.getNewFloor());
	}
	
	@Test
	public void test_getNumPassengers() {
		assertEquals(5, elevatorOrder.getNumPassengers());
	}
	
	@Test
	public void test_setNumPassengers() {
		elevatorOrder.setNumPassengers(7);
		assertEquals(7, elevatorOrder.getNumPassengers());
	}
	

}
