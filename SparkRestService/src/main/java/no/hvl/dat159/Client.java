package no.hvl.dat159;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import no.hvl.dat159.server.Temperature;

public class Client {

	private String API_DWEET_END_POINT = "dweet.io";

	private static JsonParser jsonParser = new JsonParser();
	private static String thingName = "dat159-sensor";
	

	public Client() {
				
	}
	
	public boolean publishTemp(double temp) throws IOException {
		
		JsonObject content = new JsonObject();
		
		content.addProperty("temperature", temp);
				
		thingName = URLEncoder.encode(thingName, "UTF-8");
		
		URL url = new URL("http://localhost:8080/tempsensor/current");
				
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setRequestMethod("PUT");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		PrintWriter out = new PrintWriter(connection.getOutputStream());
		
		out.println(content.toString());
		
		out.flush();
		out.close();
		
		JsonObject response = readResponse(connection.getInputStream());
		System.out.println(response.get("temperature").getAsString());

		connection.disconnect();

		return true;
	}
	
	public boolean publishHeat(boolean heat) throws IOException {
		
		JsonObject content = new JsonObject();
		
		content.addProperty("heat", heat);
				
		thingName = URLEncoder.encode(thingName, "UTF-8");
		
		URL url = new URL("http://localhost:8080/heater/current");
				
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setRequestMethod("PUT");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		PrintWriter out = new PrintWriter(connection.getOutputStream());
		
		out.println(content.toString());
		
		out.flush();
		out.close();
		
		JsonObject response = readResponse(connection.getInputStream());
		System.out.println("Heat response: " + response.get("heat").getAsString());

		connection.disconnect();

		return true;
	}

	public String getTemp() throws IOException {
		
		// http://dweet.io/get/latest/dweet/for/dat159-sensor
					
		thingName = URLEncoder.encode(thingName, "UTF-8");
		
		URL url = new URL("http://localhost:8080/tempsensor/current");
				
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestMethod("GET");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		JsonObject response = readResponse(connection.getInputStream());
//		System.out.println(response);

		connection.disconnect();
		
		return response.get("temperature").getAsString();

//		if (response.has("this") && response.get("this").getAsString().equals("succeeded"))
//		{
//			return response.toString();
//		}
//		else {
//			return null;
//		}
	}
	
public boolean getHeat() throws IOException {
		
		// http://dweet.io/get/latest/dweet/for/dat159-sensor
					
		thingName = URLEncoder.encode(thingName, "UTF-8");
		
		URL url = new URL("http://localhost:8080/heater/current");
				
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestMethod("GET");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		JsonObject response = readResponse(connection.getInputStream());
//		System.out.println(response);

		connection.disconnect();
		
		return response.get("heat").getAsBoolean();

//		if (response.has("this") && response.get("this").getAsString().equals("succeeded"))
//		{
//			return response.toString();
//		}
//		else {
//			return null;
//		}
	}
	
	private static JsonObject readResponse(InputStream in) {
		
		Scanner scan = new Scanner(in);
		StringBuilder sn = new StringBuilder();
		
		while (scan.hasNext())
			sn.append(scan.nextLine()).append('\n');
		
		scan.close();
		
		return jsonParser.parse(sn.toString()).getAsJsonObject();
	}
	
//	public static void main(String [] args) {
//		
//		try {
//			Gson gson = new Gson();
//            Temperature temp = gson.fromJson(get(), Temperature.class);
//			System.out.println(temp.getTemperature());
//			if(temp.getTemperature() <= 15) System.out.println("true");
//			else System.out.println("false");
////			if(temp.getTemperature() <= 15) publish(true);
////			else publish(false);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
