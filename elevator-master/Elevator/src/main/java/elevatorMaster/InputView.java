package elevatorMaster;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * InputView class to view and sort all configuration and commands
 * 
 * @author felixwade hyungjuncho roujunwan shahdsamour 
 *
 */

public class InputView {
	private ConfigurationController config;
	private int mode;
	private ArrayList<ElevatorOrder> orders = new ArrayList<>();
	private static final Logger LOGGER = LogManager.getLogger(ThreadController.class);

	/**
	 * contructor method for class InputView. Initialises configuration 
	 * @param config Configuration object that has data on setup requirements
	 */
	public InputView(ConfigurationController config) {
		this.config = config;
		this.mode = 0;
	}
	
	/**
	 * method that prints a welcome message once program first runs
	 */
	
	public void startUp() {
		System.out.println("Welcome to ICARUS Elevator Systems. Please input one of the following modes:\n[C] CUSTOM\n[R] RANDOM\n[D] DAY\n[N] NIGHT\nOr enter [E] EXIT to leave console.");
	}
	
	/**
	 * method that checks that command inputs are valid
	 * @param source int value that specifies the floor to pick up people 
	 * @param dest int value that specifies the floor to drop off
	 * @param startFloor int value that specifies the first floor
	 * @param endFloor int value that specifies the last floor
	 * @return true if all the inputs are valid and in range, false otherwise
	 */
	
	public boolean checkSourceAndDestinationAreValid(int source, int dest, int startFloor, int endFloor) {
		if ((source >= startFloor && source <= endFloor)&& (dest >= startFloor && dest <= endFloor) && source != dest){
			return true;
		}
		return false;
	}
	
	/**
	 * method that converts string command input into and ElevatorOrder object and checks if input is valid in the process
	 * @param input string input from console
	 */
	
	public void convertStringToInput(String input) {
		String[] pairs = input.split(",\\s*");

		for (String pair : pairs) {
			String[] numbers = pair.split(":");
			try {
				int source = Integer.parseInt(numbers[0]);
				int dest = Integer.parseInt(numbers[1]);
				int passengers = Integer.parseInt(numbers[2]);
				if(passengers > 0 && checkSourceAndDestinationAreValid(source,dest,this.config.getStartingFloor(),this.config.getEndFloor())) {
					this.orders.add(new ElevatorOrder(source,dest,passengers));
				}
				
			} catch (NumberFormatException e) {
				LOGGER.error("Invalid input: could not parse " + input);
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				LOGGER.error("Invalid input: could not parse " + input);

			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				LOGGER.error("Invalid input: could not parse " + input);

			}
		}

	}
	/**
	 * method that processes all inputs from console and decides whether they set mode, are a command, or are just invalid inputs
	 * @param input String i nput from console
	 */
	
	public synchronized void processModeInput(String input) {
		if(input.equals("C")) {
			LOGGER.trace("CUSTOM MODE ACTIVATED");
			this.mode = 1;
		}else if(input.equals("R")) {
			LOGGER.trace("RANDOM MODE ACTIVATED");
			this.mode = 2; 
			
		}else if(input.equals("D")) {
			LOGGER.trace("DAY MODE ACTIVATED");
			this.mode = 3;
		}else if(input.equals("N")) {
			LOGGER.trace("NIGHT MODE ACTIVATED");
			this.mode = 4;
		}else if(input.equals("E")) {
			LOGGER.trace("EXITING SYSTEM");
			this.mode = 5;
		}else if(this.getMode() == 0){
			LOGGER.trace("Invalid mode input please try again.");
		}else if (this.getMode() == 1) {
			this.convertStringToInput(input);
		}
		
	}
	
	/**
	 * method which adds a command to the top of the queue
	 * @param order ElevatorOrder object to be pushed in front of the list
	 */
	
	public synchronized void addToTop(ElevatorOrder order) {
		this.orders.add(0, order);
	}
	
	/**
	 * method to receive the first order in the queue and remove the order from the list
	 * @return ElevatorOrder objects that specifies the command on the top of the queue
	 */
	
	public synchronized ElevatorOrder getFirstOrder() {
		if (this.orders.size() == 0) {
			return null;
		}
		ElevatorOrder orders = this.orders.get(0);
		this.orders.remove(0);
		return orders;
	}
	
	/**
	 * method to get a random floor in a specified range
	 * @param startingFloor the lower bound of the floor for random, the first floor
	 * @param endingFloor the upper bound of the floor for random, the last floor
	 * @return an int value between startingFloor and endFloor
	 */
	
	public int getRandomFloor(int startingFloor, int endingFloor) {
		Random random = new Random();
		return random.nextInt(endingFloor-startingFloor)+startingFloor;
	}
	
	/**
	 * a method to get a random amount of passengers
	 * @param maxPassengers the capacity of passengers in an elevator to be used to set an upper bound for random, to be increased by 4
	 * @return an int value between 1 and the maxPassengers+4
	 */
	
	public int getRandomPassengers(int maxPassengers) {
		Random random = new Random();
		return random.nextInt(maxPassengers+5)+1;
	}
	
	/**
	 * method that sets all command variables to be random when random mode is activated
	 */
	
	public void randomMode() {
		int source = this.getRandomFloor(this.config.getStartingFloor(),this.config.getEndFloor()+1);
		int dest = source;
		while(source == dest) {
			dest = this.getRandomFloor(this.config.getStartingFloor(),this.config.getEndFloor()+1);
		}
		int people = this.getRandomPassengers(this.config.getMaxPassengers());
		ElevatorOrder order = new ElevatorOrder(source,dest,people);
		LOGGER.trace(source +":" + dest + ":"+people);
		this.orders.add(order);
		try {
			Thread.sleep(5000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method that sets the source floor to the starting floor and the rest of the inputs as random when day mode is activated
	 */
	
	public void dayMode() {
		int source = this.config.getStartingFloor();
		int dest = source;
		while(source == dest) {
			dest = this.getRandomFloor(this.config.getStartingFloor(),this.config.getEndFloor()+1);
		}
		int people = this.getRandomPassengers(this.config.getMaxPassengers());
		ElevatorOrder order = new ElevatorOrder(source,dest,people);
		LOGGER.trace(source +":" + dest + ":"+people);
		this.orders.add(order);
		try {
			Thread.sleep(3000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * method that sets the destination floor to the starting floor and the rest of the inputs as random when night mode is activated
	 */
	
	public void nightMode() {
		int dest = this.config.getEndFloor();
		int source = dest;
		while(source == dest) {
			source = this.getRandomFloor(this.config.getStartingFloor(),this.config.getEndFloor()+1);
		}
		int people = this.getRandomPassengers(this.config.getMaxPassengers());
		ElevatorOrder order = new ElevatorOrder(source,dest,people);
		LOGGER.trace(source +":" + dest + ":"+people);
		this.orders.add(order);
		try {
			Thread.sleep(3000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * method which specifices which random generated mode to run
	 */
	
	public void runMode() {
		if(this.getMode() == 2) {
			
			randomMode();
			
		}else if(this.getMode() == 3) {
			dayMode();
			
		}else if(this.getMode() == 4) {
			nightMode();	
		}
	}
	
	/**
	 * getter method for the attribute mode
	 * @return an int value representing the attribute mode
	 */
	
	public synchronized int getMode() {
		return this.mode;
	}
	
	/**
	 * getter method for the attribute orders
	 * @return an ArrayList of ElevatorOrder objects representing the attribute orders
	 */
	
	public synchronized ArrayList<ElevatorOrder> getOrders() {
		return this.orders;
	}
	
	/**
	 * Optimising the orders to assign each order to the nearest stationary elevator
	 */
	
	/**
	 * method to optimise the orders to assign each order to the nearest stationary elevator
	 * @param elevatorList map of all elevators to assign a command to
	 */
	
	public synchronized void optimiseOrders(Map<Integer, Elevator> elevatorList) {

		int numOrders = Math.min(orders.size(), config.getNumElevators());
		int numAssigned = 0;
		
		// Assign only the first numElevator orders to an elevator (if there are that many in the input list)
		
		while (numAssigned < numOrders) {


			ElevatorOrder nextOrder = getFirstOrder();
			int minDistance = config.getEndFloor() + 1;
			int closestElevator = -1;

			for (int i = 0; i < config.getNumElevators(); i++) {

				if ((Math.abs(nextOrder.getFloor() - elevatorList.get(i).getCurrentFloor()) < minDistance)
						&& (elevatorList.get(i).getOrder() == null)) {																	
					closestElevator = i;
					minDistance = Math.abs(nextOrder.getFloor() - elevatorList.get(i).getCurrentFloor());
				}
			}
			
			if (closestElevator == -1) {
				orders.add(nextOrder);
				return;
			}
			else {
				elevatorList.get(closestElevator).setOrder(nextOrder);
				numAssigned++;
			}
		}
	}
	
	
	/**
	 * FAILED CODE FOR OPTIMISING for multiple moving elevators
	 */
	
	// Optimising elevator commands by assigning each command to the nearest elevator that is going towards it
	// trying method with each elevator having a list of commands
	
//	public synchronized void optimiseOrders1(Map<Integer, Elevator> elevatorList) {
//
////		int numOrders = outputList.size();
//		int numOrders = Math.min(orders.size(), config.getNumElevators());
//		int numAssigned = 0;
//
//		while (numAssigned < numOrders) {
//
////			System.out.println("num orders = " + numOrders);
////			System.out.println("\n num assigned = "+numAssigned);
//
//			ElevatorOrder nextOrder = getFirstOrder();
//			int minDistance = config.getEndFloor() + 1;
//			int closestElevator = 0;
//			
//			int orderSource = nextOrder.getFloor();
//			
//			int closestStat = findClosestStationary(nextOrder, elevatorList);
////			System.out.println("closest stationary: "+closestStat);
//			
//			// prioritize the closest stationary elevator.
//			
//			if (closestStat > 0) {
//				closestElevator = closestStat;
//				System.out.println("closest stationary: "+closestStat);
//			}
//			// if all elevators are moving, find the closest elevator that is going towards the command 
//			// and maybe in the same direction 
//			else {
//				for (int i = 0; i < config.getNumElevators(); i++) {
//					
//					int elevatorLocation = elevatorList.get(i).getCurrentFloor();		// maybe instead check destination of job is closest
//					
////					System.out.println("\nchecking elevator "+elevatorList.get(i).getID()+" at floor "+elevatorLocation);
//					
//					// making sure one elevator doesn't take too many jobs
//					if (elevatorList.get(i).viewNextOrders().size() > config.getNumElevatos()) {
//						continue;
//					}
//					
//					// find the closest elevator that is moving towar
//					if (Math.abs(orderSource - elevatorLocation) < minDistance) {
//						
////						System.out.println("elevator "+elevatorList.get(i).getID());
////						System.out.println(Math.abs(orderSource - elevatorLocation)+" < "+minDistance);
//						
//						if (Math.abs(orderSource - elevatorLocation) >= Math.abs(orderSource - (elevatorLocation + elevatorList.get(i).getMovement()))) {
//							
//							// also check both commands are the same direction
////							if (sameDirection(nextOrder, elevatorList.get(i).getNextOrder1())) {
////							}
////							if (elevatorList.get(i).getNextOrder1() != null) {
////								System.out.println("elevator on job to "+elevatorList.get(i).getNextOrder1().getNewFloor());
////							}
//							closestElevator = i;
//							minDistance = Math.abs(orderSource - elevatorLocation);
//						}
//					}
//				}
//			}
//			elevatorList.get(closestElevator).addOrder(nextOrder);
//			System.out.println("assigning order " + nextOrder.getFloor() + ":" + nextOrder.getNewFloor()
//					+ " to elevator " + elevatorList.get(closestElevator).getID());
//			numAssigned++;
////			System.out.println("\nnumAssigned = "+numAssigned+" numOrders = "+numOrders);
////			if (numAssigned == numOrders) {
////				break;
////			}
//		}
////		System.out.println("returning with "+numAssigned+" assigned");
//	}
//	
//
//	// prioritise the closest stationary elevator to avoid all being assigned to one order
//	public int findClosestStationary(ElevatorOrder nextOrder, Map<Integer, Elevator> elevatorList) {
//		int closest = -1;
//		int minDistance = config.getEndFloor() + 1;
//		
//		for (int i = 0; i < config.getNumElevators(); i++) {
//			if ((Math.abs(nextOrder.getFloor() - elevatorList.get(i).getCurrentFloor()) < minDistance)
//					&& (elevatorList.get(i).getNextOrder1() == null)) {
//				
////				System.out.println("elevator "+i+" is closer than "+minDistance);
//				
//				closest = i;
//				minDistance = Math.abs(nextOrder.getFloor() - elevatorList.get(i).getCurrentFloor());
//			}
//		}
//		return closest;
//	}
//	
//	// check if two commands have the same direction 
//	
//	public boolean sameDirection(ElevatorOrder command1, ElevatorOrder command2) {
//		
//		int direction1 = command1.getNewFloor() - command1.getFloor();
//		int direction2 = command2.getNewFloor() - command2.getFloor();
//		
//		
//		return (direction1 > 0 && direction2 > 0)||(direction1 < 0 && direction2 <0);
//	}
//
//	// used for adding passengers
//	public synchronized void enqueueOrder(ElevatorOrder order) {
//		this.orders.add(order);
//	}
//
//	// removing the next order we are picking from the queue
//	public synchronized void removeFromList(int index) {
//		this.orders.remove(0);
//	}
//	
}
