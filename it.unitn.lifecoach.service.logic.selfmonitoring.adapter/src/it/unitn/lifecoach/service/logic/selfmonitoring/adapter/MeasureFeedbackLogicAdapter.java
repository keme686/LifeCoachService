/**
 * 
 */
package it.unitn.lifecoach.service.logic.selfmonitoring.adapter;

import it.unitn.lifecoach.model.Measure;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class MeasureFeedbackLogicAdapter {
	WebResource service;
	
	public MeasureFeedbackLogicAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public String getBmi(String username){
		try{
			String u = service.path("measurefeedbacklogic").path(username).path("bmi").accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getBmi  logic response: "+ response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<Measure> getDailyDataFeedReminder(String username){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurefeedbacklogic").path(username).path("dfreminder").path("daily").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param mid
	 * @return
	 */
	public String getDataFeedReminderForMeasure(String username, long mid){
		try{
			String u = service.path("measurefeedbacklogic").path(username).path("dfreminder").path(mid+"").accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		}catch(UniformInterfaceException e){
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<Measure> getB4WeekDataFeedReminder(String username){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurefeedbacklogic").path(username).path("dfreminder").path("b4week").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<Measure> getWeeklyDataFeedReminder(String username){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurefeedbacklogic").path(username).path("dfreminder").path("week").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }		
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<Measure> getAllMissingDataFeedReminder(String username){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurefeedbacklogic").path(username).path("dfreminder").path("allmissing").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 return null;
		 }catch(Exception e){
			 return null;
		 }		
	}
	/**
	 * 
	 * @param username
	 * @param mid
	 * @param days
	 * @return
	 */
	public double getTotalValue(String username, long mid, int days){
		try{
			double u = service.path("measurefeedbacklogic").path(username).path("total").path(mid+"").path(""+days).accept(MediaType.APPLICATION_JSON).get(Double.class);
			 return u;
		}catch(UniformInterfaceException e){
			 return -2.0;
		 }catch(Exception e){
			 return -2.0;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param mid
	 * @param days
	 * @return
	 */
	public double getAverageValue(String username, long mid, int days){
		try{
			double u = service.path("measurefeedbacklogic").path(username).path("average").path(mid+"").path(""+days).accept(MediaType.APPLICATION_JSON).get(Double.class);
			 return u;
		}catch(UniformInterfaceException e){
			 return -2.0;
		 }catch(Exception e){
			 return -2.0;
		 }
	}
	
	/**
	 * 
	 * @return uri - for local service end
	 */
	private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://127.0.1.1:5909/").build();
	}
}
