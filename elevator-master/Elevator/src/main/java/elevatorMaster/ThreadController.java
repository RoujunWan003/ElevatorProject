package elevatorMaster;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * ThreadController class to create a thread for a single Elevator object and call methods to move the elevators
 * 
 * @author felixwade hyungjuncho roujunwan shahdsamour 
 *
 */
public class ThreadController extends Thread {
	
	private Elevator elevator;
	private JsonController json;
	private InputView input;
	private final int MAX_PASSENGERS; // added in
	private static final Logger LOGGER = LogManager.getLogger(ThreadController.class);
	
	private Map<Integer, Elevator> elevatorList;
	
	/**
	 * constructor for ThreadController class. Prepares Elevator objects to be utilised
	 * @param elevator Elevator object for thread
	 * @param json JsonController to constantly update json file
	 * @param input InputView object to access modes and commands
	 * @param maxPassengers int value which specifies the capacity of people in elevator
	 * @param elevatorList Map of all elvators to compare options for optimisation
	 */
	public ThreadController(Elevator elevator, JsonController json, InputView input, int maxPassengers, Map<Integer, Elevator> elevatorList) {
		this.elevator = elevator;
		this.json = json;
		this.input = input;
		this.MAX_PASSENGERS = maxPassengers;
		this.elevatorList = elevatorList;
	}
	
	/**
	 * method that creates a new command for passengers who hwere unable to fit in an elevator due to maximum capacity being reached
	 * @param order
	 * @param passengers
	 */
	
	public void createCommandForLeftoverPassengers(ElevatorOrder order, int passengers) {
		if(passengers > 0) {
			ElevatorOrder nextCommand = new ElevatorOrder(order.getFloor(), order.getNewFloor(), passengers);
			input.addToTop(nextCommand);
		}
	}
	
	/**
	 * getter method for the attribute input
	 * @return InputView object that is sotred in the attribute input
	 */
	
	public InputView getInput() {
		return this.input;
	}
	
	/**
	 * override runnable method to cutomise how threads run
	 */
	@Override
	public void run() {
		while (input.getMode() < 5) {
			if (this.input.getOrders().size() > 0 && this.elevator.getMovement() == 0) {
				// optimising orders by assigning to nearest stationary elevator
				this.input.optimiseOrders(elevatorList);
				ElevatorOrder order = elevator.getOrder();
				if (order != null) {
					int sourceFloor = order.getFloor();
					int destFloor = order.getNewFloor();
					int addedPassengers = order.getNumPassengers();
					int passengers = this.elevator.getNumberPassengers() + addedPassengers;
					int extra = 0;
					if(passengers > MAX_PASSENGERS) {
						extra  = passengers - MAX_PASSENGERS;
						passengers = MAX_PASSENGERS;
						addedPassengers -=extra;
					}
					LOGGER.trace("Elevator " + this.elevator.getID()  + " will pick up " + passengers
							+ " people from floor " + sourceFloor + " and drop off at floor " + destFloor);
					moveElevators(sourceFloor, addedPassengers, order, extra, destFloor);
				}
			}
		}
	}
	
	/**
	 * method to move elevators using the command given
	 * @param sourceFloor int value that specifies the floor to pick up passengers
	 * @param addedPassengers int value that specifies the added passengers onto the elvator
	 * @param order ElevatorOrder object that is the command the elevator implements
	 * @param extra int value that specifies the extra passengers that could not fit into the elevator
	 * @param destFloor int value that specifies the elevator needs to stop at
	 */
	
	public void moveElevators(int sourceFloor, int addedPassengers, ElevatorOrder order, int extra, int destFloor) {
		this.elevator.setOrder(order);
		moveToFloor(sourceFloor);
		this.elevator.addPassengers(addedPassengers);
		try {
			Thread.sleep(1000);
			LOGGER.trace("Elevator " + this.elevator.getID() + " doors Opening on floor " + this.elevator.getCurrentFloor() + " and has " + this.elevator.getNumberPassengers() + " passengers");
			Thread.sleep(1000);
			LOGGER.trace("Elevator doors Closing");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createCommandForLeftoverPassengers(order,extra);
		moveToFloor(destFloor);
		try {
			Thread.sleep(1000);
			LOGGER.trace("Elevator " + this.elevator.getID() + " doors Opening"); 
			Thread.sleep(1000);
			LOGGER.trace("Elevator " + this.elevator.getID() + " doors Closing and " + this.elevator.getNumberPassengers() + " passengers getting off");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.elevator.removePassengers(addedPassengers);
		this.json.updateList(this.elevator);
		this.json.serialiseList();
		this.elevator.setOrder(null);
	}
	
	/**
	 * method that detects how the elevator is moving depending on the floor its on and the floor it is trying to reach
	 * @param floorOne int value of the current floor the elevator is on
	 * @param floorTwo int value of the floor the elevator needs to reach
	 * @return an int value which specifices if a floor is moving upwards (1), downwards (-1), or still (0)
	 */
	
	public int detectMovement(int floorOne, int floorTwo) {
		int result = floorOne - floorTwo;
		if (result < 0) {
			return 1;
		} else if (result > 0) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * method to move an elevator one level
	 * @param direction int value that either increments or decrements floor
	 */
	
	public void moveAFloor(int direction) {
		try {
			Thread.sleep(1000);
			this.elevator.setCurrentFloor(this.elevator.getCurrentFloor() + direction);
			
			this.json.updateList(this.elevator);
			this.json.serialiseList();

			LOGGER.trace("Elevator " + this.elevator.getID() + " on Floor " + this.elevator.getCurrentFloor());
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * method that moves an elevator to a floor more than one level
	 * @param floor int value of the floor the elevator needs to move to
	 */

	public void moveToFloor(int floor) {

		this.elevator.setMovement(detectMovement(this.elevator.getCurrentFloor(), floor));

		while (this.elevator.getMovement() != 0 && this.input.getMode() < 5) {
			moveAFloor(this.elevator.getMovement());
			this.elevator.setMovement(detectMovement(this.elevator.getCurrentFloor(), floor));
		}
		if (this.elevator.getMovement() == 0 && this.input.getMode() < 5) {
			try {
				Thread.sleep(2000);
				LOGGER.trace("Elevator " + this.elevator.getID() + " arrived at Floor " + this.elevator.getCurrentFloor()
						+ " with " + this.elevator.getNumberPassengers() + " passengers.");
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}
}
