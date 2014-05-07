/**
 * 
 */
package it.unitn.lifecoach.service.logic.selfmonitoring;

import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.UserDataAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/userlogic")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class UserResource {

	/**
	 * User data adapter which users jersey client adapter to call the Data storage REST service
	 */
	UserDataAdapter adapter = new UserDataAdapter();
	
	UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();

	/**
	 *  create a new profile 
	 *  
	 * @param user
	 * @return
	 */
	@POST	
	public User addUser(User user){
		if(user!=null && !authAdapter.exists(user))
			return adapter.addUser(user);
		else return null;
	}
	
	/**
	 * get User information
	 *  
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}")
	public User getUser(@PathParam("username")String username){
		return adapter.getUser(username);
	}
	/**
	 * Update User information; username, status and token cannot be updated using this method
	 * 
	 * @param user
	 * @param username
	 * @return
	 */
	@PUT
	@Path("{username}")
	public User updateUser(User user, String username){
		User u = adapter.getUser(username);		
		if(u==null || user == null)
			return null;
		
		user.setUsername(username);
		user.setStatus(u.getStatus());
		user.setAccessToken(u.getAccessToken());
		
		return adapter.updateUser(user, username);
	}
	/**
	 * Deactivates a user account
	 * 
	 * @param username
	 * @return
	 */
	@PUT
	@Path("{username}/deactivate")
	public User deactivateUser(@PathParam("username")String username){
		User user = adapter.getUser(username);
		if(user==null)
			return null;
		user.setStatus(false);
		
		return adapter.updateUser(user, username);
	}
	/**
	 * Activates a user Account
	 * 
	 * @param username
	 * @return
	 */
	@PUT
	@Path("{username}/activate")
	public User activateUser(@PathParam("username")String username){
		User user = adapter.getUser(username);
		if(user==null)
			return null;
		user.setStatus(true);		
		return adapter.updateUser(user, username);
	}
	
	
}
