/**
 * 
 */
package it.unitn.lifecoach.service.process.schedule.adapter;

import it.unitn.lifecoach.model.Schedule;

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
 * Adapter for the Schedule Process Service
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class ScheduleProcessAdapter {

	
	
	WebResource service;
	
	public ScheduleProcessAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @param username
	 * @return
	 */
	public List<Schedule> getSchedulesWithinInterval(long from, long to , String username){	
		try{
			GenericType<List<Schedule>> genericListType =  new GenericType<List<Schedule>>() {};	                
			List<Schedule> u = service.path("schedule").path("process").path("interval")
					.queryParam("from",""+from). queryParam("to", to+"").queryParam("username", username)
					 .accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	public List<Schedule> getSchedulesForToday(String username){	
		try{
			GenericType<List<Schedule>> genericListType =  new GenericType<List<Schedule>>() {};	                
			List<Schedule> u = service.path("schedule").path("process").path("today")
					.queryParam("username", username).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	public List<Schedule> getSchedulesWthinAnHour(String username){	
		try{
			GenericType<List<Schedule>> genericListType =  new GenericType<List<Schedule>>() {};	                
			List<Schedule> u = service.path("schedule").path("process").path("remind")
					.queryParam("username", username).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @param sid
	 * @return
	 */
	public Schedule getSchedule(long sid){	
		try{	                
			Schedule u = service.path("schedule").path("process").path("get")
					.queryParam("sid", ""+sid).accept(MediaType.APPLICATION_JSON)
					.get(Schedule.class);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 return null;
		}catch(Exception e){
			 return null;
		}
	}
	
	
	public Schedule updateSchedule(Schedule schedule,long sid, String username){	
		try{	                
			Schedule u = service.path("schedule").path("process").path("update")
					.queryParam("sid", ""+sid).queryParam("username", username).
					type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).
					put(Schedule.class,schedule);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 return null;
		}catch(Exception e){
			 return null;
		}
	}
	
	public Schedule addSchedule(Schedule schedule,String username){	
		try{	                
			Schedule u = service.path("schedule").path("process").path("add")
					.queryParam("username", username).type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).post(Schedule.class,schedule);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 return null;
		}catch(Exception e){
			 return null;
		}
	}
	
	public String deleteSchedule(long sid ,String username){	
		try{	                
			String u = service.path("schedule").path("process").path("del").queryParam("sid",""+sid)
					.queryParam("username", username).accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	 * @return uri - for local service end
	 */
	private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://127.0.1.1:5935/").build();
	}
	
	
}
