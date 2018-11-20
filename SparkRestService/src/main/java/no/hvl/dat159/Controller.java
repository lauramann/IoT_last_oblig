package no.hvl.dat159;

import java.io.IOException;

public class Controller implements Runnable {

	@Override
	public void run() {
		Client cl = new Client();
		while(true) {
			try {
				String temp = cl.getTemp();
				double tempd = Double.parseDouble(temp);
				
				if(tempd<=15) {
					cl.publishHeat(true);
				}
				else cl.publishHeat(false);
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
