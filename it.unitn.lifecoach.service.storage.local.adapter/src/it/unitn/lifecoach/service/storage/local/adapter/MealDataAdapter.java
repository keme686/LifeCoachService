/**
 * 
 */
package it.unitn.lifecoach.service.storage.local.adapter;

import it.unitn.lifecoach.model.MealLog;

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
 * Adapter for the MealDao 
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class MealDataAdapter {

	WebResource service;
	
	public MealDataAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	
	/**
	 * 
	 * @param log
	 * @return
	 */
	public MealLog addMealLog(MealLog log){
		try{
			 MealLog u = service.path("meallogdata").path("add").type(MediaType.APPLICATION_JSON).
					 accept(MediaType.APPLICATION_JSON).post(MealLog.class, log);
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
	 * @param log
	 * @param mid
	 * @return
	 */
	public MealLog updateMealLog(MealLog log, long mid){
		try{
			 MealLog u = service.path("meallogdata").path(mid+"").path("update").
					 type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(MealLog.class, log);
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
	 * @param mid
	 * @return
	 */
	public String deleteMealLog(long mid){
		try{
			 String u = service.path("meallogdata").path(mid+"").path("del").accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	 * @return
	 */
	public List<MealLog> getAll(){
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meallogdata").path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	public List<MealLog> getAll(String username){
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meallogdata").path(username).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getAll(String username) logic: "+response.getStatus());
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
	    return UriBuilder.fromUri("http://127.0.1.1:5900/").build();
	}

}