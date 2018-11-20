package no.hvl.dat159;

import java.io.IOException;

public class Heating implements Runnable {

	private Room room;
	Client heatcl = new Client();
		
	public Heating (Room room) {
		this.room = room;
	}
	
	public void write (boolean newvalue) {
		System.out.println("SWITCH: " + newvalue);
		room.actuate(newvalue);
	}

	@Override
	public void run() {
		while(true) {
			try {
				boolean heatSwitch = heatcl.getHeat();
					write(heatSwitch);
					Thread.sleep(1000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}
