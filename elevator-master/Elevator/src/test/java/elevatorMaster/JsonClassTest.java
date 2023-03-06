package elevatorMaster;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.*;
import java.util.ArrayList;
import java.util.List;


public class JsonClassTest {
	
	JsonController json; 
	
	Elevator elevator;
	Map<Integer, Elevator> elevators;
	
	
	@BeforeEach
	void setup() {
		
		json = JsonController.getInstance(); ;
		elevators = new HashMap<Integer,Elevator>();
		elevator = new Elevator(0,5,1);
		
	}
/*	public void addToList(Elevator elevator) {
		this.elevators.put(elevator.getID(), elevator);
	}


	

	*/
	@Test
	public void test_getElevatorList() {
		
		json.addToList(elevator);
		
		assertEquals(json.getElevatorList().get(0),elevator);
	}
	
	@Test
	public void test_setList() {
		
		
		elevators.put(0,elevator);
		
		json.setList(elevators);
		
		assertEquals(elevators, json.getElevatorList());
		
	}
	
	@Test
	public void updateList() {

		
		elevators.put(0,elevator);
		
		json.setList(elevators);
		
		Elevator elevatorNew = new Elevator(0,6,5);
		
		json.updateList(elevatorNew);
		
		assertEquals(elevatorNew, json.getElevatorList().get(0));
		
	}
	
	@Test
	public void addToList() {
		
		
		elevators.put(0,elevator);
		
		json.setList(elevators);
		
		Elevator elevatorNew = new Elevator(1,6,5);
		
		json.addToList(elevatorNew);
		
		assertEquals(2, json.getElevatorList().size());
		
	}
	
	@Test
	public void test_getElevatorArrayList() {
		
		elevators.put(0,elevator);
		
		json.setList(elevators);
		
		Elevator elevatorNew = new Elevator(1,6,5);
		json.addToList(elevatorNew);
		List<Elevator> elevatorArrayList = new ArrayList<Elevator>();
		elevatorArrayList.add(elevator);
		elevatorArrayList.add(elevatorNew);
		
		assertEquals(elevatorArrayList, json.getElevatorArrayList());
	}
	
	


}
