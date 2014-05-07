/**
 * 
 */
package it.unitn.lifecoach.service.process.goaltracking;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.service.logic.goaltracking.adapter.GoalLogicAdapter;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;

/**
 * This resource is used to manage {@link Goal} related information of a user
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/goaltracking")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class GoalProcessResource {

	/**
	 * Adapter for Goal logic layer service
	 */
	private GoalLogicAdapter adapter = new GoalLogicAdapter();
	/**
	 * Adapter for Authentication service
	 */
	private UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();
	
	/**
	 * create a new plan/goal. If a goal is associated with a measure data and if an ACTIVE goal associated with 
	 * the same measure doesn't exist. Otherwise, the user should cancel the other goal to start over.
	 * 
	 * @param goal  a new {@link Goal} object to be stored
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return goal information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@POST
	@Path("{username}")
	public Response addGoal(Goal goal, @PathParam("username")String username, @QueryParam("access_token")String token){
		if(goal == null || username ==  null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.addGoal(goal, username)).build();
	}
	/**
	 * get a goal information
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param gId id of a goal
	 * @return goal information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{username}/{gid}")
	public Response getGoal(@PathParam("username")String username,@PathParam("gid") long gId, @QueryParam("access_token")String token){
		if(username == null || gId == 0 || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.getGoal(username, gId)).build();
	}
	/**
	 * update goal information
	 * 
	 * @param goal updated goal information
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param gId id of a goal to be updated
	 * @return goal information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@PUT
	@Path("{username}/{gid}")
	public Response updateGoal(Goal goal, @PathParam("username")String username, @PathParam("gid") long gId, @QueryParam("access_token")String token){
		if(goal == null || username == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.updateGoal(goal, username, gId)).build();
	}
	/**
	 * Cancel goal tracking 
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param gId id of a goal to be canceled
	 * @return canceled goal information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@DELETE
	@Path("{username}/{gid}")
	public Response cancelGoal(@PathParam("username")String username, @PathParam("gid") long gId, @QueryParam("access_token")String token){
		if(username == null || gId == 0 || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.cancelGoal(username, gId)).build();
	}
	/**
	 * get list of active goals the system is tracking
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return {@link List} of {@link Goal} information if successful, Response 204 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{username}/active")
	public List<Goal> getActiveGoals(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getActiveGoals(username);
	}
	/**
	 * Get active goal associated with a measure mid
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param mid id of a measure 
	 * @return goal information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{username}/{mid}/active")
	public Response getActiveGoal(@PathParam("username")String username, @PathParam("mid") long mid, @QueryParam("access_token")String token){
		if(username == null || mid ==0 || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.getActiveGoal(username, mid)).build();
	}
	/**
	 * Get list of canceled goals
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return {@link List} of {@link Goal} information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{username}/canceled")
	public List<Goal> getCanceledGoals(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getCanceledGoals(username);
	}
	/**
	 * Get list of all goals regardless of goal status
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return {@link List} of {@link Goal} information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{username}/all")
	public List<Goal> getAll(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getAll(username);
	}
	/**
	 * get list of achieved goals so far
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return {@link List} of {@link Goal} information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{username}/achieved")
	public List<Goal> getAchievedGoals(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getAchievedGoals(username);
	}
	/**
	 * get list of goals a user lost achieving 
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return {@link List} of {@link Goal} information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{username}/lost")
	public List<Goal> getLostGoals(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getLostGoals(username);
	}
	/**
	 * get list of goals associated with a measure mid, regardless of goal status
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param mid id of a measure
	 * @return {@link List} of {@link Goal} information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{username}/{mid}/all")
	public List<Goal> getAll(@PathParam("username")String username, @PathParam("mid") long mid, @QueryParam("access_token")String token){
		if(username == null || mid == 0 || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getAll(username, mid);
	}
	/**
	 * Get list of datapoints entered by the user for the specified goal
	 * 
	 * @param gId id of a goal
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return {@link List} of {@link Datapoint} information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@GET
	@Path("{gid}/datapoints")
	public List<Datapoint> getDatapoints(@PathParam("gid") long gId, @QueryParam("username")String username, @QueryParam("access_token")String token){
		if(gId == 0 || username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getDatapoints(gId);
	}
	/**
	 * add a new datapoint for an ACTIVE goal 
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param gid  id of a goal this datapoint is going to be added
	 * @param dp	new datapoint to be stored
	 * @return {@link Datapoint} information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@POST
	@Path("{username}/{gid}/datapoint.add")
	public Response addDatapoint(@PathParam("username")String username, @PathParam("gid") long gid, Datapoint dp, @QueryParam("access_token")String token){
		if(username == null || gid == 0 || dp == null || token == null)
		return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.addDatapoint(username, gid, dp)).build();
	}
	/**
	 * update a datapoint for an ACTIVE  goal
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param gid
	 * @param dp
	 * @return {@link Datapoint} information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@PUT
	@Path("{username}/{gid}/datapoint.update")
	public Response updateDatapoint(@PathParam("username")String username, @PathParam("gid") long gid, Datapoint dp, @QueryParam("access_token")String token){
		if(username == null || gid == 0 || dp == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.updateDatapoint(username, gid, dp)).build();
	}
	/**
	 * delete datapoint information of an ACTIVE goal
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param gid
	 * @param dpId
	 * @return deletion information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	@DELETE
	@Path("{username}/{gid}/{dpId}/datapoint.del")
	public Response deleteDatapoint(@PathParam("username")String username, @PathParam("gid") long gid, @PathParam("dpId") long dpId, @QueryParam("access_token")String token){
		if(username == null || gid == 0 || dpId == 0 || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.deleteDatapoint(username, gid, dpId)).build();
	}
//////////////////////////////////////////////////////////////
//////////GOAL FEEDBACKS ///////////////////////////////////
//////////////////////////////////////////////////////////
	
	/**
	 * get expected data value by a specified date
	 * 
	 * @param date - date for expected data
	 * @param gId - goal id
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return	expected value of current datapoint by this date
	 * 			204 -if goal doesn't exist 
	 */
	@GET
	@Path("expected")
	public Response getExpectedGoalValue(@QueryParam("date") long date, @QueryParam("gid")long gId, @QueryParam("access_token")String token, @QueryParam("username")String username){
		if(date ==0 || gId == 0 || token == null || username == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.getExpectedGoalValue(date, gId)).build();
	}
	/**
	 * get feedback for a goal at the specified date
	 * 
	 * @param date
	 * @param gId
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		0 - Doing as planned
	 * 				1 - Doing more than planned, Better
	 * 				-1 - Doing under planned rate 
	 */
	@GET
	@Path("feedback")
	public Response getFeedback(@QueryParam("date") long date, @QueryParam("gid")long gId, @QueryParam("access_token")String token, @QueryParam("username")String username){
		if(date == 0 || gId == 0 || token == null || username == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.getFeedback(date, gId)).build();
				
	}
	/**
	 * Get progress report for specified goal
	 * 
	 * @param date - progress checking date
	 * @param gId - goal id
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return -2 Red - means needs more than 160% rate to achieve goal from initial rate estimated<br/>
	 * 			-1 Yellow - means needs up to 160% rate to achieve a goal<br/>
	 * 			0  Green - means progress on track <br/>
	 * 			Response 204 - if goal doesn't exist<br/>
	 */
	@GET
	@Path("progress")
	public Response getProgress(@QueryParam("date") long date, @QueryParam("gid")long gId, @QueryParam("access_token")String token, @QueryParam("username")String username){
		if(date == 0 || gId == 0 || token  == null || username == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.getProgress(date, gId)).build();
	}
}
