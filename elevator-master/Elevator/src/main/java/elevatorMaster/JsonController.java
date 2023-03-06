package elevatorMaster;

/**
 * JsonController class which serialises and deserialises elevator properties
 * 
 * @author felixwade hyungjuncho roujunwan shahdsamour 
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonController {
	private Map<Integer, Elevator> elevators = new HashMap<Integer,Elevator>();
	private File destination;
	private static JsonController instance;
	
	
	/**
	 *  private constructor to prevent external instantiation
	 */
	private JsonController() {
	}
	
	/**
	 * Method which creates an instance of JsonController object
	 * @return instance pof JsonController object
	 */
	
	public static JsonController getInstance() {
		if (instance == null) {
			instance = new JsonController();
		}
		return instance;
	}
	
	/**
	 * method which adds an Elevator object to map attribute elevators
	 * @param elevator Elevator object to add to map
	 */
	
	public void addToList(Elevator elevator) {
		this.elevators.put(elevator.getID(), elevator);
	}
	
	/**
	 * method to update an Elevator object in the map attribute elevators
	 * @param elevator  Elevator object to be updated in map
	 */
	
	public void updateList(Elevator elevator) {
		this.elevators.replace(elevator.getID(),elevator);
	}
	
	/**
	 * stter method for the attribute elevators
	 * @param elevatorList
	 */
	
	public void setList(Map<Integer,Elevator> elevatorList) {
		this.elevators = elevatorList;
	}
	
	/**
	 * getter method for the attribute elevators
	 * @return map of current value for the attribute elevators
	 */
	
	public Map<Integer,Elevator> getElevatorList(){
		return this.elevators;
	}
	
	/**
	 * method to serialise all elevator objects into a json file
	 */
	
	public void serialiseList() {
		DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
		pp = pp.withObjectIndenter(new DefaultIndenter("  ", "\n"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDefaultPrettyPrinter(pp);
		try {
			destination = new File("elevator.json");
			mapper.writeValue(destination, this.elevators);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method that deserialises the "Elevator.json" file and reads all Elevator objects stored in a file and stores it in a map
	 * @return map of stored Elevator objects
	 */
	
	 public Map<Integer, Elevator> deserializeElevator() {

		ObjectMapper mapper = new ObjectMapper();
		
		File destination = new File("Elevator.json");
		
		try {
	        TypeReference<Map<Integer,Elevator>> typeRef = new TypeReference<Map<Integer,Elevator>>() {};
	        return mapper.readValue(destination, typeRef);
		} catch (JsonParseException e) {
			
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	 
	 /**
	  * method which converts the map attribute elevators into an ArrayList of Elevator objects
	  * @return ArrayList of Elevator objects stored in the attribute elevators
	  */
	 
	public ArrayList<Elevator> getElevatorArrayList(){
		ArrayList<Elevator> elevatorList = new ArrayList<>();
		for(int i = 0; i < this.elevators.size(); i++) {
			elevatorList.add(elevators.get(i));
		}
		return elevatorList;
	}
}

