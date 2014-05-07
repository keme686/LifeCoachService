/**
 * 
 */
package it.unitn.lifecoach.service.process.authentication;

import java.security.SecureRandom;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.storage.local.adapter.UserDataAdapter;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/userauthentication")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class UserAuthenticationResource {

	UserDataAdapter adapter = new UserDataAdapter();
	
	/**
	 * get access token to access applications' api, as a session token
	 *  
	 * @param user
	 * @return
	 */
	@POST
	@Path("login")
	public Response login(User user){
		if(user == null)
			return Response.noContent().build();
		User u = adapter.getUser(user.getUsername());
		if( u !=null && u.getUsername().equals(user.getUsername())
				&& u.getPassword() != null && u.getPassword().equals(user.getPassword()) ){
				
			long as = new SecureRandom().nextLong();
			u.setAccessToken(u.getUsername()+":"+as+":"+new Date().getTime());			
				
			adapter.updateUser(u, u.getUsername());
				
			return Response.ok(u).build();			
		}
		return Response.noContent().build();
	}
	/**
	 * remove access token from user information
	 * 
	 * @param user
	 * @return
	 */
	@POST
	@Path("logout")
	public Response logout(User user){
		if(user == null)
			return Response.noContent().build();
		
		User u = adapter.getUser(user.getUsername());
			if( u !=null && u.getUsername().equals(user.getUsername()) 
					&& u.getAccessToken() !=null && u.getAccessToken().equals(user.getAccessToken())
					&& u.getPassword() != null && u.getPassword().equals(user.getPassword()) ){
				
				u.setAccessToken(null);							
				adapter.updateUser(u, u.getUsername());
				
				return Response.status(200).build();			
		}
		return Response.noContent().build();
	}
	
	@POST
	@Path("auth")
	public Response authenticate(User user){
		if(user == null)
			return Response.noContent().build();
		
		User u = adapter.getUser(user.getUsername());
			if( u !=null && u.getUsername().equals(user.getUsername()) 
					&& u.getAccessToken() !=null && u.getAccessToken().equals(user.getAccessToken()) ){
				
				return Response.ok(true+"").build();			
		}else
			return Response.ok(false+"").build();
	}	
	
	@POST
	@Path("exists")
	public Response exists(User user){
		if(user == null)
			return Response.noContent().build();
		
		User u = adapter.getUser(user.getUsername());
			if( u != null){				
				return Response.ok(true+"").build();			
		}else
			return Response.ok(false+"").build();
	}
	
	
}
