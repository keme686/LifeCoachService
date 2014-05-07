/**
 * 
 */
package it.unitn.lifecoach.service.storage.local.adapter;

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
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class ScheduleDataAdapter {

WebResource service;
	
	public ScheduleDataAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	
	/**
	 * 
	 * @param schedule
	 * @return
	 */
	public Schedule addSchedule(Schedule schedule){
		try{
		Schedule s = service.path("scheduledata").path("add").type(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).post(Schedule.class, schedule);
		return s;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 e.printStackTrace();
			 return null;
		}catch(Exception e){
			 e.printStackTrace();
			 return null;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Schedule getSchedule(long id){
		
		try{
			Schedule s = service.path("scheduledata").path(""+id).
					accept(MediaType.APPLICATION_JSON).get(Schedule.class);
			return s;
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
	 * @param id
	 * @param schedule
	 * @return
	 */
	public Schedule updateSchedule(long id , Schedule schedule){
		
		try{
			Schedule s = service.path("scheduledata").path(""+id).path("update").type(MediaType.APPLICATION_JSON).
					accept(MediaType.APPLICATION_JSON).put(Schedule.class, schedule);
			return s;
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
	 * @param id
	 * @return
	 */
	public String  deleteSchedule(long id ){
		
		try{
			String str= service.path("scheduledata").path(""+id).path("del").accept(MediaType.APPLICATION_JSON).delete(String.class);
			return str;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 return null;
		}catch(Exception e){
			 return null;
		}
	}
	
	public List<Schedule> getAll(){
		try{
			GenericType<List<Schedule>> genericListType =  new GenericType<List<Schedule>>() {};
			List<Schedule> list= service.path("scheduledata").path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return list;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 return null;
		}catch(Exception e){
			 return null;
		}
	}
	
	public List<Schedule> getAll(String username){
		try{
			GenericType<List<Schedule>> genericListType =  new GenericType<List<Schedule>>() {};
			List<Schedule> list= service.path("scheduledata").path(username).path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return list;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 return null;
		}catch(Exception e){
			 return null;
		}
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 * @return uri - for local service end
	 */
	private static URI getBaseURI() {
	    return UriBuilder.fromUri("http://127.0.1.1:5900/").build();
	}

}
