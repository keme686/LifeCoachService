/**
 * 
 */
package it.unitn.lifecoach.service.process.selfmonitoring;

import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.logic.selfmonitoring.adapter.UserLogicAdapter;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class is used to manage {@link User} related information such as creating new, updating existing information, 
 * getting existring information using a unique id or activation and deactivation of an account
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/selfmonitoring")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class UserProcessResource {
	
	/**
	 * Adapter for accessing a user logic service
	 */
	private UserLogicAdapter adapter = new UserLogicAdapter();
	
	/**
	 * Adapter for authentication service 
	 */
	private UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();
	
	/**
	 *  Create a new user profile to the system 
	 *  
	 * @param user		a {@link User} object to be stored/created. 
	 * 					Its <code>name</code>, <code>username</code> and <code> password</code> properties should not be null.
	 * @return		A newly created {@link User} data if successful, 
	 * 				null - otherwise 
	 */
	@POST
	@Path("user")
	public User addUser(User user){
		if(user != null && user.getName()!=null && user.getUsername()!=null && user.getPassword()!=null){
			return adapter.addUser(user);
		}else 
			return null;
		
	}
	/**
	 * Get User information stored in the system. 
	 * @param username		username of a user to retrieve 
	 * @param token			authentication token generated for this user during login time
	 * @return		Response 204 if username or/and token are null or if user with specified username doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link User} detail information<br /> 
	 */
	@GET
	@Path("user/{username}")
	public Response getUser(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		User u = adapter.getUser(username);
		if(u == null)
			Response.noContent().build();
		return Response.ok(u).build();
	}
	/**
	 * Update User information; <code>username</code>, <code>status</code> and token cannot be updated using this method
	 * 
	 * @param user  updated user information	
	 * @param username	id of a user to be updated
	 * @param token  authentication token generated while the user logs in
	 * @return 		Response 204 if user, username or/and token are null or if user with specified username doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link User} detail information<br /> 
	 */
	
	@PUT
	@Path("user/{username}")
	public Response updateUser(User user, @PathParam("username") String username, @QueryParam("access_token")String token){		
		if(user == null || username == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		User u = adapter.updateUser(user, username);
		if(u == null)
			Response.notModified().build();
		return Response.ok(u).build();
	}
	/**
	 * Deactivates a user account
	 * 
	 * @param username 	 id of a user to deactivate
	 * @param token  
	 * @return		Response 204 if username or/and token are null or if user with specified username doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link User} detail information if deactivate successful<br /> 
	 */
	@PUT
	@Path("user/{username}/deactivate")
	public Response deactivateUser(@PathParam("username")String username, @QueryParam("access_token")String token){		
		if(username == null || token == null) 
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		User u = adapter.deactivateUser(username);
		if(u == null)
			Response.notModified().build();
		return Response.ok(u).build();
	}
	
	/**
	 * Activates a user Account
	 * 
	 * @param username		id of a user to activate
	 * @param token  
	 * @return		Response 204 if username or/and token are null or if user with specified username doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link User} detail information if activation successful<br /> 
	 */
	@PUT
	@Path("user/{username}/activate")
	public Response activateUser(@PathParam("username")String username, @QueryParam("access_token")String token){		
		if(username == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		User u = adapter.activateUser(username);
		if(u == null)
			Response.notModified().build();
		return Response.ok(u).build();
	}
	
}
