package no.hvl.dat159;

public class RoomDevice {

	public static void main(String[] args) {
		
		Room room = new Room(20);
		
		TemperatureSensor sensor = new TemperatureSensor(room);
		Controller controller = new Controller();
		Heating heating = new Heating(room);

		
		try {
			
			Thread temppublisher = new Thread(sensor);
			Thread controllerThread = new Thread(controller);
			Thread heatingThread = new Thread(heating);
			
			// TODO: add heating subscriber running i
			
			//ad controller here
			
			temppublisher.start();
			controllerThread.start();
			heatingThread.start();

			temppublisher.join();
			controllerThread.join();
			heatingThread.join();
			
		} catch (Exception ex) {
			
			System.out.println("RoomDevice: " + ex.getMessage());
			ex.printStackTrace();
		}

	}

}
