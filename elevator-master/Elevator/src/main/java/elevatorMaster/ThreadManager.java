package elevatorMaster;
import java.util.ArrayList;
import java.util.Map;

/**
 * ThreadManager class which manages all threads of the ThreadController class by intialising, starting, and ending them
 * 
 * @author felixwade hyungjuncho roujunwan shahdsamour 
 *
 */

public class ThreadManager {
	ArrayList<ThreadController> threads = new ArrayList<>();
	
	/**
	 * method to set up all threads in elevator system
	 * @param elevatorList a map of all elevators with each eelvator being assigned to a single thread
	 * @param input an InputView object to implement in the ThreadRunner objects created
	 * @param json a JsonController object to implement in the ThreadRunner objects created
	 * @param maxPassengers the maximum number of passengers that are allowed on a single elevator
	 */
	
	public void setUpThreads(Map<Integer, Elevator> elevatorList, InputView input, JsonController json, int maxPassengers) {
		for(int i = 0; i < elevatorList.size(); i++) {
			this.threads.add(new ThreadController(elevatorList.get(i), json, input, maxPassengers, elevatorList));
		}	
	}
	
	/**
	 * getter method for the attribute threads
	 * @return ArrayList of ThreadController objects from the attribute threads
	 */
	
	public ArrayList<ThreadController> getThreads(){
		return this.threads;
	}
	
	/**
	 * method to start all threads within the map attribute threads
	 */
	
	public void startThreads() {
		for(ThreadController thread : this.threads) {
			thread.start();
		}
	}
	
	/**
	 * method to end all threads within the map attribute threads
	 */
	
	public void endThreads() {
		for(ThreadController thread : this.threads) {
			try {
				thread.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
