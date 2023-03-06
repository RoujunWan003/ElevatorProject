package elevatorMaster;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;
import java.util.Random;


public class InputTest {
	
	InputView input;
	
	ElevatorOrder elevatorOrder;
	
	ConfigurationController configuration;
	
	@BeforeEach
	void setup() {
		
		configuration = new ConfigurationController(3,20,0,5,false);
		input = new InputView(configuration);
		
		
	}

	@Test
	public void test_checkSourceAndDestinationAreValidFalse() {
		
		assertFalse(input.checkSourceAndDestinationAreValid(2, 10, 5, 7));
	}
	
	@Test
	public void test_checkSourceAndDestinationAreValidTrue() {
		
		assertTrue(input.checkSourceAndDestinationAreValid(11, 13, 5, 15));
	}

	@Test
	public void test_convertStringToInput() {
		
		configuration = new ConfigurationController(3,20,0,5,false);
		InputView input4 = new InputView(configuration);
		
		input4.convertStringToInput("3:1:5,6:5:2,7:8:1,1:10:3");
		
		
		ArrayList<ElevatorOrder> targetoutputList = new ArrayList<ElevatorOrder>(); 
		
		ElevatorOrder elevatorOrders1 = new ElevatorOrder(3,1,5);
		
		ElevatorOrder elevatorOrders2 = new ElevatorOrder(6,5,2);
		
		ElevatorOrder elevatorOrders3 = new ElevatorOrder(7,8,1);
		
		ElevatorOrder elevatorOrders4 = new ElevatorOrder(1,10,3);
		
		
		targetoutputList.add(elevatorOrders1);
		
		targetoutputList.add(elevatorOrders2);
		
		targetoutputList.add(elevatorOrders3);
		
		targetoutputList.add(elevatorOrders4);
		
		
		for (int i = 0; i < input.getOrders().size();i++) {
			assertEquals(targetoutputList.get(i).getFloor(), input.getOrders().get(i).getFloor());
			assertEquals(targetoutputList.get(i).getNewFloor(), input.getOrders().get(i).getNewFloor());
			assertEquals(targetoutputList.get(i).getNumPassengers(),input.getOrders().get(i).getNumPassengers());
		}
	
		
	}

	@Test
	public void test_processModeCUSTOM() {
		input.processModeInput("C");
		assertEquals(1,input.getMode());
		
	}
	
	@Test
	public void test_processModeRANDOM() {
		input.processModeInput("R");
		assertEquals(2,input.getMode());
		
	}
	
	@Test
	public void test_processModeDAY() {
		input.processModeInput("D");
		assertEquals(3,input.getMode());
		
	}
	@Test
	public void test_processModeNIGHT() {
		input.processModeInput("N");
		assertEquals(4,input.getMode());
		
	}
	@Test
	public void test_processModeEXIT() {
		input.processModeInput("E");
		assertEquals(5,input.getMode());
		
	}


	@Test
	public void test_addToTop() {
		
		ElevatorOrder elevatorOrder1 = new ElevatorOrder(2,4,6);
		ElevatorOrder elevatorOrder2 = new ElevatorOrder(2,2,8);
		
		input.getOrders().add(elevatorOrder1);
		input.addToTop(elevatorOrder2);
		
		assertEquals(elevatorOrder2,input.getOrders().get(0));
	}

	@Test
	public void test_getFirstOrder() {
		ElevatorOrder elevatorOrder1 = new ElevatorOrder(2,4,6);
		ElevatorOrder elevatorOrder2 = new ElevatorOrder(2,2,8);
		
		input.getOrders().add(elevatorOrder1);
		input.getOrders().add(elevatorOrder2);
		
		assertEquals(elevatorOrder1,input.getFirstOrder());
	}

	@Test
	public void test_getMode() {
		assertEquals(0,input.getMode());
	}
	
	@Test
	public void test_getRandomFloor() {
		int floor = input.getRandomFloor(1, 10);
		assertTrue(floor >= 1 && floor < 10);
	}
	
	@Test
	public void test_getRandomPassengers() {
		int passengers = input.getRandomPassengers(10);
		assertTrue(passengers >= 1 && passengers < 15);
	}

}
