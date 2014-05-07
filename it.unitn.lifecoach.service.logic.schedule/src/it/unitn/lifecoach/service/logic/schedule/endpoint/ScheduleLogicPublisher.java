package it.unitn.lifecoach.service.logic.schedule.endpoint;

import it.unitn.lifecoach.service.logic.schedule.ws.ScheduleLogicImpl;

import javax.xml.ws.Endpoint;

public class ScheduleLogicPublisher {

	public static String SERVER_URL = "http://localhost";
	public static String PORT = "6905";
	public static String BASE_URL = "/ws/schdlogic";
	
	public static String getEndpointURL() {
		return SERVER_URL+":"+PORT+BASE_URL;
	}
 
	public static void main(String[] args) {
		String endpointUrl = getEndpointURL();
		System.out.println("Starting Food Info Service...");
		System.out.println("--> Published at = "+endpointUrl);
		Endpoint.publish(endpointUrl, new ScheduleLogicImpl());
    }
}
