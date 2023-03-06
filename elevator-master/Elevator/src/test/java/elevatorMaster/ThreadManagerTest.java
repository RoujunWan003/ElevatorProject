package elevatorMaster;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


import java.util.HashMap;
import java.util.Map;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;



import java.util.HashMap;
import java.util.Map;


public class ThreadManagerTest {
	
	ThreadController threadController;
	JsonController json;
	ConfigurationController configurationController;
	ThreadManager threadManager;

	@BeforeEach
	void setup() {
		
		threadManager = new ThreadManager ();
		json = JsonController.getInstance();
		
		configurationController = new ConfigurationController(3,20,0,5,false);
		
	}
	@Test
	public void test_setUpThreads() {
		
		InputView input5 = new InputView(configurationController);
		
		Elevator elevator5 = new Elevator(5,10,0);
		
		Elevator elevator6 = new Elevator(6,2,0);
		
		Elevator elevator7 = new Elevator(7,3,0);
		
		Map<Integer, Elevator> elevatorList = new HashMap<Integer, Elevator> ();
		
		elevatorList.put(0, elevator5);
		
		elevatorList.put(1, elevator6);
		
		elevatorList.put(2, elevator7);
		
		threadManager.setUpThreads(elevatorList, input5, json, 15);
		
		assertEquals(3,threadManager.getThreads().size());
	}
	
	
	
}
