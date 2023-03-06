package elevatorMaster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Controller class which reads the file "confiurations.json" and stores the variable conditions to help setup the elevator
 * @author felixwade hyungjuncho roujunwan shahdsamour  
 *
 */
public class ConfigurationController {
	
	private int endFloor;
	private int numElevators;
	private int startingFloor;
	private int maxPassengers;
	private boolean jsonState;
	
	/**
	 * Constructor for controller class which deserialises the configurations.json
	 * @param numElevators int value which shows the number of elevators in the elevator system
	 * @param endFloor int value that shows the final floor in elevators
	 * @param startingFloor int value that shows the starting floor in elevators
	 * @param maxPassengers int value that shows the maximum capacity of passengers in one elevator
	 * @param jsonState boolean value which returns whether or not program should use a previous elevator state
	 */
	@JsonCreator
	public ConfigurationController(@JsonProperty("Number of Elevators")int numElevators, @JsonProperty("End Floor")int endFloor, 
			@JsonProperty("Starting Floor")int startingFloor, @JsonProperty("Max people per command")int maxPassengers,
			@JsonProperty("Use Previous Json State")boolean jsonState) {
		
		this.numElevators = numElevators;
		this.endFloor = endFloor;
		this.startingFloor = startingFloor;
		this.maxPassengers = maxPassengers;
		this.jsonState = jsonState;
	}
	
	/**
	 * getter method for attribute endFloor
	 * @return the current value of endFloor
	 */
	
	public int getEndFloor() {
		return this.endFloor;
	}
	
	/**
	 * getter method for the attribute numElevators
	 * @return the current value of numElevators
	 */
	
	public int getNumElevators() {
		return this.numElevators;
	}
	
	/**
	 * getter method for the attribute startingFloor
	 * @return the current value of startingFloor
	 */
	
	public int getStartingFloor() {
		return this.startingFloor;
	}
	
	/**
	 * getter method for the attribute maxPassengers
	 * @return the current value of maxPassengers
	 */
	
	public int getMaxPassengers() {
		return this.maxPassengers;
	}
	
	/**
	 * getter method for the attribute previousState
	 * @return the current value of previousState
	 */
	
	public boolean usePreviousState() {
		return jsonState;
	}
}
