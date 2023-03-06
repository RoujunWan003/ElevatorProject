package elevatorMaster;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Elevator class which sets up elevator objects to be used in threads
 * @author felixwade hyungjuncho roujunwan shahdsamour  
 *
 */
public class Elevator implements FrameGUI {
	
	
	private int id;
	private int currentFloor;
	private int movement; //1 for upwards, -1 for downwards, 0  for still
	private int numberPassengers; 		// initialised to 0 when elevator created
	private ElevatorOrder order = null;

	
	/*public Elevator() {
		
	}*/
	
	/**
	 * constructor method for elevator. initialises the number of passengers variable to zero
	 * @param id int value which identifies elevators from one another
	 * @param currentFloor int value which represents the current floor the elevator is on
	 * @param movement int value which represents the movement of the elevator, 1 for moving upwards, 0 for stationary and -1 for moving downwards
	 */
	
	public Elevator(int id, int currentFloor, int movement) {
		this.id = id;
		this.currentFloor = currentFloor;
		this.movement = movement;
		this.numberPassengers = 0;
	}
	
	
	/**
	 * method from FrameGUI interface which transforms the current ElevatorOrder object given to elevator into a string
	 * @return the source floor, destination floor, and number of passengers into a string command
	 */
	
	@Override
	public String getCommand() {
		String command = new String("");
		if(this.order != null) {
			command = this.order.getFloor() + ":" + this.order.getNewFloor() + ":" + this.order.getNumPassengers();
		}else {
			command = "no command given";
		}
		return command;
	}
	
	/**
	 * getter method from FrameGUI interface of the numberPassengers attribute
	 * @return an int value of the current value of the numberPassengers attribute
	 */
	
	@Override
	public int getPeople() {
		return this.numberPassengers;
	}
	
	/**
	 * getter method from FrameGUI interface which returns the state of the elevator
	 * @return a State enum that represents if the elevator is moving, stopiing, or idle
	 */
	
	@Override
	public State getState() {
		State state;
		if(this.order == null) {
			state = State.IDLE;
		}else if(this.movement == 0) {
			state = State.STOPPING;
		}else {
			state = State.MOVING;
		}
		return state;
	}
	
	/**
	 * a method from the FrameGUI interface that creates a string specifying the elevator ID
	 * @return a string of the word elevator followed by its ID
	 */
	
	@Override
	public String getInfo() {
		String info = new String("Elevator ");
		info  = info + this.id;
		return info;
	}
	
	/**
	 * a setter method that sets an object value to the attribute order
	 * @param order
	 */
	
	public void setOrder(ElevatorOrder order) {
		this.order = order;
	}
	
	/**
	 * a getter method for attribute order
	 * @return an ElevatorOrder object value of the attribute order
	 */
	
	public ElevatorOrder getOrder() {
		return this.order;
	}
	
	/**
	 * a getter method from the interface FrameGUI for the attribute currentFloor
	 * @return int value of the attribute currentFloor
	 */

	@Override
	public int getCurrentFloor() {
		return this.currentFloor;
	}

	/**
	 * a setter method that sets an int value for the attribute currentFloor
	 * @param currentFloor
	 */
	
	
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	
	/**
	 * a getter method for the attribute movement
	 * @return an int value representing the attribute movement
	 */

	public int getMovement() {
		return movement;
	}
	
	/**
	 * a setter method that sets an int value for the attribute movement
	 * @param movement
	 */

	public void setMovement(int movement) {
		this.movement = movement;
	}
	
	/**
	 * a getter method for the attribute id
	 * @return an int value representing the attribute id
	 */

	public int getID() {
		return id;
	}
	
	/**
	 * a method which increases the number of people in an elevator
	 * @param passengers
	 */
	
	public void addPassengers(int passengers) {
		this.numberPassengers += passengers;
	}
	
	/**
	 * a method which decreases the number of people in an elevator
	 * @param passengers
	 */
	
	public void removePassengers(int passengers) {
		this.numberPassengers -= passengers;
	}
	
	/**
	 * failed code for optimising multiple moving elevators
	 */
	
//	public void addOrder(ElevatorOrders order) {
//		this.nextOrders.add(order);
//	}
//	
//	public ElevatorOrders getNextOrder1() {
//		if (nextOrders.size() > 0) {
//			ElevatorOrders nextOrder1 = nextOrders.get(0);
//			return nextOrder1;
//		}
//		return null;
//	}
//	
//	public void removeOrder() {
//		this.nextOrders.remove(0);
//	}
//	
//	public ArrayList<ElevatorOrders> viewNextOrders() {
//		return this.nextOrders;
//	}
	
	
}
