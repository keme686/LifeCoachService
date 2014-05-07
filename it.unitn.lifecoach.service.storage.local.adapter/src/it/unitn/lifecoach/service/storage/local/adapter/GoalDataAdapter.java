/**
 * 
 */
package it.unitn.lifecoach.service.storage.local.adapter;

import it.unitn.lifecoach.model.Goal;

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
public class GoalDataAdapter {

	WebResource service;
	
	public GoalDataAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	/**
	 * 
	 * @param goal
	 * @return
	 */
	public Goal addGoal(Goal goal){
		try{
			Goal u = service.path("goaldata").path("add").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Goal.class, goal);
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
	 * @param gId
	 * @return
	 */
	public Goal getGoal(long gId){
		try{
			Goal u = service.path("goaldata").path(gId+"").accept(MediaType.APPLICATION_JSON).get(Goal.class);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getGoal(long gId) storage: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param goal
	 * @param gId
	 * @return
	 */
	public Goal updateGoal(Goal goal, long gId){
		try{
			Goal u = service.path("goaldata").path(gId+"").path("update").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Goal.class, goal);
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
	 * @param gId
	 * @return
	 */
	public String deleteGoal(long gId){
		try{
			String u = service.path("goaldata").path(""+gId).path("del").accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	public List<Goal>  getAll(){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaldata").path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	public List<Goal> getAll(String username){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaldata").path(username).path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	public  List<Goal> getAll(String username, long mid){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaldata").path(username).path(""+mid).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	    return UriBuilder.fromUri("http://127.0.1.1:5900/").build();
	}
}
