package elevatorMaster;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Runner class to run threads and elevator system
 * 
 * @author felixwade hyungjuncho roujunwan shahdsamour 
 *
 */
public class Runner {
	
	/**
	 * method to set configurations
	 * @return ConfigurationController object which specifies all properties for setup
	 */
	public static ConfigurationController getConfigs() {

		File configurationFile = new File("configurations.json");
		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(configurationFile, ConfigurationController.class);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	/**
	 * method to create elevators from default
	 * @param configuration ConfigurationController object that contains the properties of elevators
	 * @param JsonController to store default elevators in json file
	 * @return map of all Elevators
	 */
	
	public static Map<Integer, Elevator> createElevators(ConfigurationController configuration, JsonController json){
		Map<Integer, Elevator> elevators = new HashMap<Integer,Elevator>();

		for (int i = 0; i < configuration.getNumElevators(); i++) {
			Elevator elevator = new Elevator(i, configuration.getStartingFloor(), 0);
			elevators.put(i, elevator);
			json.addToList(elevator);
			json.serialiseList();
		}
		return elevators;
	}

	/**
	 * main method to run code and input from console
	 * @param args
	 */

	public static void main(String[] args) {
		ConfigurationController config = getConfigs();
		InputView input = new InputView(config);
		JsonController.getInstance();
		Map<Integer, Elevator> elevatorList = createElevators(config,JsonController.getInstance());
		ThreadManager threadManager = new ThreadManager();

		threadManager.setUpThreads(elevatorList, input, JsonController.getInstance(), config.getMaxPassengers());

		input.startUp();

		Thread thread = new Thread(new Runnable() {
			public void run() {
				while(true) {
					input.runMode();
					if(input.getMode() == 5) {
						break;
					}
				}
			}
		});
		if(config.usePreviousState()) {
			elevatorList = JsonController.getInstance().deserializeElevator();
			JsonController.getInstance().setList(elevatorList);
			JsonController.getInstance().serialiseList();
		}else {
			elevatorList = createElevators(config, JsonController.getInstance());
		}
		FrameView frameView = new FrameView(config.getStartingFloor(), config.getEndFloor(), config.getNumElevators(),JsonController.getInstance().getElevatorArrayList(),JsonController.getInstance(),input);
		Thread graphics = new Thread(frameView);
		graphics.start();
		threadManager.startThreads();
		thread.start();
		if(input.getMode() != 5) {
			Scanner scanner = new Scanner(System.in);
			while(scanner.hasNextLine()){
				input.processModeInput(scanner.nextLine());
				if(input.getMode() == 5) {
					break;
				}
			}
			scanner.close();
		}
		threadManager.endThreads();
		try {
			thread.join();
			graphics.join();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
