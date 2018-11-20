package no.hvl.dat159;

import java.io.IOException;

public class TemperatureSensor implements Runnable {

	private Room room;

	public TemperatureSensor(Room room) {

		this.room = room;
	}

	public double read() {

		return room.sense();
	}

	@Override
	public void run() {
		Client c = new Client();
		while(true)
		{
			double temp = read();
			try {
				c.publishTemp(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
