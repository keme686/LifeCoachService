/**
 * 
 */
package it.unitn.lifecoach.service.storage.local.adapter;

import it.unitn.lifecoach.model.User;

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
public class UserDataAdapter {

	WebResource service;
	
	public UserDataAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	public User getUser(String username){
		 try {
			 User u = service.path("userdata").path(username).accept(MediaType.APPLICATION_JSON).get(User.class);
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
	 * @param user
	 * @return
	 */
	public User addUser(User user){
		 try {
			 User u = service.path("userdata").path("add").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(User.class, user);
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
	 * @param user
	 * @param username
	 * @return
	 */
	public User updateUser(User user, String username){
		try{
			User u = service.path("userdata").path(username).path("update").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(User.class, user);
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
	public String deleteUser(String username){
		try{
			String u = service.path("userdata").path(username).path("del").accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	public List<User> getAll(){
		try{
			GenericType<List<User>> genericListType =  new GenericType<List<User>>() {};	                
			List<User> u = service.path("userdata").path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
