/**
 * 
 */
package it.unitn.lifecoach.service.process.authentication.adapter;

import it.unitn.lifecoach.model.User;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class UserAuthenticationAdapter {
	WebResource service;
	
	public UserAuthenticationAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	/**
	 * log in user
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user){
		if(user == null)
			return null;
		try{
			User u = service.path("userauthentication").path("login").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(User.class, user);
			return u;
		}catch(UniformInterfaceException e){
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * log in user 
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password){
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		return login(u);
		
	}
	/**
	 * logout user
	 * 
	 * @param user
	 * @return
	 */
	public boolean logout(User user){
		if(user == null)
			return false;
		try{
			Response status = service.path("userauthentication").path("logout").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Response.class, user);
			int s = status.getStatus();
			if(s == 200)
				return true;
			else 
				return false;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return false;
		 }catch(Exception e){
			 return false;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param token
	 * @return
	 */
	public boolean isAuthenticated(String username, String token){
		User u = new User();
		u.setUsername(username);
		u.setAccessToken(token);
		return isAuthenticated(u);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean isAuthenticated(User user){
		if(user == null)
			return false;
		try{
			String statuss = service.path("userauthentication").path("auth").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(String.class, user);			
			return Boolean.parseBoolean(statuss);
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return false;
		 }catch(Exception e){
			 return false;
		 }
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean exists(User user){
		if(user == null)
			return false;
		try{
			boolean status = service.path("userauthentication").path("exists").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Boolean.class, user);			
			return status;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return false;
		 }catch(Exception e){
			 return false;
		 }
	}
	/**
	 * 
	 * @return uri - for local service end
	 */
	private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://127.0.1.1:5922/").build();
	}
}
