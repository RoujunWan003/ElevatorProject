package elevatorMaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.*;


@ExtendWith(MockitoExtension.class)
public class ThreadControllerTest {
	
	ThreadController threadController;
	JsonController json;
	ConfigurationController configurationController;
	
	int movement = 0;
	int currentFloor = 0;
	


	@Mock
	Elevator elevatorMock;
	
	@Mock
	InputView inputMock ;
	
	@Mock
	Map<Integer, Elevator> elevatorListMock;
		
	@BeforeEach
	void setup() {
		
		json = JsonController.getInstance();
		threadController = new ThreadController(elevatorMock,json,inputMock,15,elevatorListMock);
		configurationController = new ConfigurationController(3,20,0,5,false);
		inputMock = new InputView(configurationController);
	}
	

	@Test
	public void test_detectMovement_moveup() {
		
		assertEquals(1,threadController.detectMovement(1, 5));
		
	}
	
	@Test
	public void test_detectMovement_movedown() {
		
		assertEquals(-1,threadController.detectMovement(10, 5));
		
	}
	
	@Test
	public void test_detectMovement_noMovement() {
		
		assertEquals(0,threadController.detectMovement(10, 10));
		
	}
	

	
	@Test
	public void test_moveAFloor_up() {
		
		Elevator elevator = new Elevator(0,2,5);
		threadController =  new ThreadController(elevator,json,inputMock,15,elevatorListMock);
		
		int direction = 1;
				
		threadController.moveAFloor(direction);
		
		assertEquals(elevator.getCurrentFloor(), 3);
	}
	
	@Test
	public void test_moveAFloor_down() {
		
		Elevator elevator = new Elevator(0,5,2);
		threadController =  new ThreadController(elevator,json,inputMock,15,elevatorListMock);
		
		int direction = -1;
				
		threadController.moveAFloor(direction);
		
		assertEquals(elevator.getCurrentFloor(), 4);
	}
	
	@Test
	public void test_moveAFloor_still() {
		
		Elevator elevator = new Elevator(0,3,3);
		threadController =  new ThreadController(elevator,json,inputMock,15,elevatorListMock);
		
		int direction = 0;
				
		threadController.moveAFloor(direction);
		
		assertEquals(elevator.getCurrentFloor(), 3);
	}
	
	@Test
	public void test_moveToFloor_down() {
		Elevator elevator = new Elevator(0,5,2);
		threadController =  new ThreadController(elevator,json,inputMock,15,elevatorListMock);
		threadController.moveToFloor(2);
		assertEquals(elevator.getCurrentFloor(), 2);
		
	}
	
	@Test
	public void test_moveToFloor_up() {
		Elevator elevator = new Elevator(0,2,5);
		threadController =  new ThreadController(elevator,json,inputMock,15,elevatorListMock);
		threadController.moveToFloor(5);
		assertEquals(elevator.getCurrentFloor(), 5);
		
	}
		

	
}	
	

	


