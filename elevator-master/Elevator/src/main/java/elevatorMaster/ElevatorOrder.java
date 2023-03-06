package elevatorMaster;
/**
 * ElevatorOrder class which stores the specifications of elevator commands
 * 
 * @author felixwade hyungjuncho roujunwan shahdsamour 
 *
 */
public class ElevatorOrder {
	private int floor;
	private int newFloor;
	private int numPassengers;
	
	/**
	 * constructor method for ElevatorOrder class
	 * @param floor int value of the source floor
	 * @param newFloor int value of the floor to travel to
	 * @param passengers int value which represents the number of people requesting this command
	 */
	
	public ElevatorOrder(int floor, int newFloor, int passengers) {
		this.floor = floor;
		this.newFloor = newFloor;
		this.numPassengers = passengers;
	}
	
	/**
	 * a getter method for the attribute floor
	 * @return an int value representing the attribute floor
	 */
	
	public int getFloor() {
		return floor;
	}
	
	/**
	 * a setter method that sets an int value for the attribute floor
	 * @param floor
	 */
	
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	/**
	 * a getter method for the attribute newFloor
	 * @return an int value representing the attribute newFloor
	 */
	
	public int getNewFloor() {
		return newFloor;
	}
	
	/**
	 * a setter method that sets an int value for the attribute newFloor
	 * @param newFloor
	 */
	
	public void setNewFloor(int newFloor) {
		this.newFloor = newFloor;
	}
	
	/**
	 * a getter method for the attribute numPassengers
	 * @return an int value representing the attribute numPassengers
	 */
	
	public int getNumPassengers() {
		return numPassengers;
	}
	
	/**
	 * a setter method that sets an int value for the attribute numPassengers
	 * @param numPassengers
	 */
	
	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}
}
