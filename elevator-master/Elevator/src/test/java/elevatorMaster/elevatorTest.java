package elevatorMaster;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class elevatorTest {
	
	Elevator elevator;
	
	@Mock
	ElevatorOrder orderMock;
	
	@BeforeEach
	void setup() {
		
		elevator = new Elevator(5,10,1);

	}
	@Test 
	public void test_getCurrentFloor() {
		
		assertEquals(10,elevator.getCurrentFloor());
	}
	@Test 
	public void test_setCurrentFloor() {
		
		elevator.setCurrentFloor(11);
		
		assertEquals(11,elevator.getCurrentFloor());
	}
	
	@Test 
	public void test_getMovement() {
		
		assertEquals(1,elevator.getMovement());
	}
	
	@Test 
	public void test_setMovement() {
		
		elevator.setMovement(-1);
		
		assertEquals(-1,elevator.getMovement());
	}
	
	@Test 
	public void test_getID() {
		
		assertEquals(5,elevator.getID());
	}
	//Default number of people 0
	@Test
	public void test_getNumberPassengers() {
		
		assertEquals(0,elevator.getPeople());
	}
	
	// Test adding a passenger to an elevator
	@Test
	public void test_adding_passenger_to_elevator() {
	
		elevator.addPassengers(2);
		
		assertEquals(2,elevator.getPeople());
	}
	
	
	// Test removing a passenger from an elevator
	@Test
	public void test_removing_passenger_from_elevator() {
		
		elevator.addPassengers(4);
		
		elevator.removePassengers(3);
		
		assertEquals(1,elevator.getPeople());
	}
	
	@Test
	public void test_getOrder_initial() {
		assertNull(elevator.getOrder());
	}
	
	@Test
	public void test_setOrder_then_getOrder() {
		assertNotNull(orderMock);
		elevator.setOrder(orderMock);
		assertEquals(orderMock, elevator.getOrder());
	}
	
	@Test
	public void test_getCommand_no_order() {
		assertEquals("no command given", elevator.getCommand());
	}
	
	@Test
	public void test_getCommand_order_not_null() {
		assertNotNull(orderMock);
		elevator.setOrder(orderMock);
		when(orderMock.getFloor()).thenReturn(1);
		when(orderMock.getNewFloor()).thenReturn(4);
		when(orderMock.getNumPassengers()).thenReturn(3);
		String command = elevator.getCommand();
		verify(orderMock, times(1)).getFloor();
		verify(orderMock, times(1)).getNewFloor();
		verify(orderMock, times(1)).getNumPassengers();
		assertEquals("1:4:3", command);
	}
	
	@Test
	public void test_getInfo() {
		assertEquals("Elevator 5",elevator.getInfo());
	}
	
	@Test
	public void test_getState_order_null() {
		assertEquals(State.IDLE, elevator.getState());
	}
	
	@Test
	public void test_getState_zero_movement() {
		assertNotNull(orderMock);
		elevator.setOrder(orderMock);
		elevator.setMovement(0);
		assertEquals(State.STOPPING, elevator.getState());
	}
	@Test
	public void test_getState_upwards_movement() {
		assertNotNull(orderMock);
		elevator.setOrder(orderMock);
		elevator.setMovement(1);
		assertEquals(State.MOVING, elevator.getState());
	}
	
	@Test
	public void test_getState_downwards_movement() {
		assertNotNull(orderMock);
		elevator.setOrder(orderMock);
		elevator.setMovement(-1);
		assertEquals(State.MOVING, elevator.getState());
	}
	
	

}
