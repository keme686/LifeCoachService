/**
 * 
 */
package it.unitn.lifecoach.storage.service;

import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.storage.dao.GoalDAO;

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

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/goaldata")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class GoalDataResource {

	/**
	 * goal data access object for local database
	 */
	private GoalDAO goaldao = new GoalDAO();
	/**
	 * 
	 * @param goal
	 * @return
	 */
	@POST
	@Path("add")
	public Goal addGoal(Goal goal){
		try{
			return goaldao.addGoal(goal);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 
	 * @param golaId
	 * @param goal
	 * @return
	 */
	@PUT
	@Path("{goalId}/update")
	public Goal updateGoal(@PathParam("goalId")long golaId, Goal goal){
		try{
			return goaldao.update(goal);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 
	 * @param goalId
	 * @return
	 */
	@DELETE
	@Path("{goalId}/del")
	public String deleteGoal(@PathParam("goalId")long goalId){
		try{
			boolean st = goaldao.delete(goalId);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting Goal\"}";
		}
	}
	
	/**
	 * 
	 * @param goalId
	 * @return
	 */
	@GET
	@Path("{goalId}")
	public Goal getGoal(@PathParam("goalId")long goalId){
		try{
			return goaldao.get(goalId);
		}catch(Exception e){
			System.out.println("GoalDataResource: getGoal()");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/all")
	public List<Goal> getGoal(@PathParam("username")String username){
		try{
			return goaldao.getAll(username);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * get list of goals associated with a measureId for the user 
	 * @param username
	 * @param measureId
	 * @return
	 */
	@GET
	@Path("{username}/{measureId}")
	public List<Goal> addGoal(@PathParam("username")String username, @PathParam("measureId")long measureId){
		try{
			return goaldao.getAll(username, measureId);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("all")
	public List<Goal> addAllGoals(){
		try{
			return goaldao.getAll();
		}catch(Exception e){
			return null;
		}
	}
}
