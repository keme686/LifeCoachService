package it.unitn.lifecoach.service.storage.out.food.endpoint;

import it.unitn.lifecoach.service.storage.out.food.ws.FoodInfoImpl;

import javax.xml.ws.Endpoint;

/**
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class FoodInfoPublisher {
	public static String SERVER_URL = "http://localhost";
	public static String PORT = "6902";
	public static String BASE_URL = "/ws/foodinfo";
	
	public static String getEndpointURL() {
		return SERVER_URL+":"+PORT+BASE_URL;
	}
 
	public static void main(String[] args) {
		String endpointUrl = getEndpointURL();
		System.out.println("Starting Food Info Service...");
		System.out.println("--> Published at = "+endpointUrl);
		Endpoint.publish(endpointUrl, new FoodInfoImpl());
    }
}
