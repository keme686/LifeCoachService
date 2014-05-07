/**
 * 
 */
package it.unitn.lifecoach.storage.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.storage.dao.UserDAO;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/userdata")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class UserDataResource {

	private UserDAO userdao = new UserDAO();
	/**
	 * add a new user to the database 
	 * 
	 * @param user
	 * @return the user added, null otherwise 
	 */
	@POST
	@Path("add")
	public User addUser(User user){
		try{
			user = userdao.addUser(user);
		}catch(Exception e){
			return null;
		}
		return user;
	}
	
	/**
	 * update user data
	 * 
	 * @param username
	 * @param user
	 * @return updated user data, null otherwise
	 */
	@PUT
	@Path("{username}/update")
	public User updateUser(@PathParam("username")String username, User user){
		try{
			user = userdao.update(user);
		}catch(Exception e){
			return null;
		}
		return user;
	}	
	/**
	 * deletes user data from the database 
	 * 
	 * @param username
	 * @return deletion status or error msg as Json string
	 */
	@DELETE
	@Path("{username}/del")
	public String deleteUser(@PathParam("username")String username){
		try{
			boolean st = userdao.delete(username);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting user\"}";
		}
	}
	
	/**
	 * get user information
	 * 
	 * @param username
	 * @return a user identified by username specified, null otherwise
	 */
	@GET
	@Path("{username}")
	public User getUser(@PathParam("username")String username){
		try{
			User u = userdao.get(username);
			return u;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @return list of users in a database, null otherwise
	 */
	@GET
	@Path("all")
	public List<User> getAllUsers(){
		try{
			return userdao.getAll();
		}catch(Exception e){
			return null;
		}
	}
	
}
