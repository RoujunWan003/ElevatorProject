package elevatorMaster;

import org.junit.jupiter.api.BeforeEach;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;



public class ConfigurationTest {
	
	ConfigurationController configuration;
	
	@BeforeEach
	void setup() {
		
		configuration = new ConfigurationController(3,20,0,5,false);
		
		
	}

	
	@Test 
	public void test_getEndFloor() {
		assertEquals(20,configuration.getEndFloor());
	}
	
	@Test 
	public void test_getNumElevators(){
		assertEquals(3,configuration.getNumElevators());
	}
	
	@Test 
	public void test_getStartingFloor(){
		assertEquals(0,configuration.getStartingFloor());
	}
	
	@Test 
	public void test_usePreviousState(){
		assertFalse(configuration.usePreviousState());
	}
	
	@Test 
	public void test_getMaxPassengers(){
		assertEquals(5,configuration.getMaxPassengers());
	}
	

}