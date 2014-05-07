/**
 * 
 */
package it.unitn.lifecoach.service.logic.goaltracking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unitn.lifecoach.Constants.GoalStatus;
import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.storage.local.adapter.DatapointDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.GoalDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.UserDataAdapter;

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
@Path("/goallogic")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class GoalResource {

	/**
	 * Goal data adapter to communicate with Goal data resource in Data Storage service
	 */
	GoalDataAdapter adapter = new GoalDataAdapter();
	/**
	 * Data point adapter to communicate with Data point storage resource service
	 */
	DatapointDataAdapter dpAdapter = new DatapointDataAdapter();
	/**
	 * User data adapter to communicate with User data resource in Data storage service
	 */
	UserDataAdapter userAdapter = new UserDataAdapter();
	
	/**
	 * Set a new Goal to start tracking
	 * 
	 * @param username
	 * @param goal
	 * @return created {@link Goal} if success, null otherwise
	 */
	@POST
	@Path("{username}")
	public Goal addGoal(@PathParam("username") String username, Goal goal){
		if (goal.getMeasure() != null) {
			List<Goal> gls = adapter
					.getAll(username, goal.getMeasure().getId());

			if (gls != null) {
				for (Goal g : gls) {
					if (g.getStatus() != null
							&& g.getStatus().equals(GoalStatus.ACTIVE))
						return null;
				}
			}
		}
		User u = userAdapter.getUser(username);
		calculateRate(goal);
		goal.setStatus(GoalStatus.ACTIVE);
		goal.setUser(u);
		goal.setUpdatedTime(new Date().getTime());
		return adapter.addGoal(goal);
	}
	
	private void calculateRate(Goal goal){		
		if(goal.getGoalValue() > 0.0 && goal.getGoalDate() > 0.0){
			double val = goal.getGoalValue() - goal.getInitialValue();
			
			long mils = goal.getGoalDate() - new Date().getTime();
			long days = mils / (24*3600*1000);
			double rate = val/days; 
			goal.setRate(rate);
			if(val > 0){
				goal.setPlannedDirection(1);
			}else
				goal.setPlannedDirection(-1);
		}else if(goal.getGoalDate()>0 && goal.getRate()>0.0){
			long mils = goal.getGoalDate() - new Date().getTime();
			long days = mils / (24*3600*1000);
			double amount = days* goal.getRate();
			goal.setGoalValue(goal.getInitialValue() + amount);
			if(goal.getRate()>0){
				goal.setPlannedDirection(1);				
			}else
				goal.setPlannedDirection(-1);
		}else{			
		}
	}
	/**
	 * update Goal information
	 * 
	 * @param goal
	 * @param username
	 * @param gId
	 * @return updated {@link Goal} object if success, null otherwise
	 */
	@PUT
	@Path("{username}/{gid}")
	public Goal updateGoal(Goal goal,@PathParam("username")String username, @PathParam("gid") long gId){
		User u = userAdapter.getUser(username);
		if(u==null)
			return null;
		if(goal.getStatus()!=null && !goal.getStatus().equals(GoalStatus.ACTIVE))
			return null;
		goal.setUser(u);
		calculateRate(goal);
		checkAndUpdateGoal(goal);
		goal.setUpdatedTime(new Date().getTime());
		return adapter.updateGoal(goal, gId);
	}
	
	/**
	 * Cancel Goal tracking
	 *  
	 * @param username
	 * @param gId
	 * @return status of the action as true/false, or Error info if exception occurs
	 */
	@DELETE
	@Path("{username}/{gid}")
	public Goal cancelGoal(@PathParam("username")String username, @PathParam("gid")long gId){
		//To be decided: whether to delete a goal OR flag the status as 'Canceled'
		Goal g =  adapter.getGoal(gId);
		User u = userAdapter.getUser(username);
		
		if(g==null || u == null) return null;
		
		g.setStatus(GoalStatus.CANCELED);
		return adapter.updateGoal(g, gId);
	}
	
	/**
	 * get Goal information using id of a goal
	 * 
	 * @param username
	 * @param gId
	 * @return a {@link Goal} object if exists, 
	 * 			null otherwise
	 */
	@GET
	@Path("{username}/{gid}")
	public Goal getGoal(@PathParam("username")String username, @PathParam("gid")long gId){
		
		Goal g =  adapter.getGoal(gId);
		User u = userAdapter.getUser(username);
		
		if(g==null || u == null) {
			return null;
		}
		
		return g;
	}
	
	/**
	 * get active goals for tracking
	 * 
	 * @param username
	 * @return list of {@link Goal}s with status "Active" if exists,
	 * 			empty list if no Goal found
	 */
	@GET
	@Path("{username}/active")
	public List<Goal> getActiveGoals(@PathParam("username")String username){
		
		List<Goal> goals = adapter.getAll(username);
		List<Goal> actives = new ArrayList<>();
		
		if(goals == null) return new ArrayList<>();
		
		for(Goal g: goals){
			checkAndUpdateGoal(g); //check whether this goal already finalized or not
			if(g.getStatus() !=null && g.getStatus().equals(GoalStatus.ACTIVE) ){
				
				actives.add(g);
			}
		}
		
		return actives;
	}
	/**
	 * checks whether the goal date is less or equal to today, and set the goal as ACHIVED OR LOST 
	 * @param g the goal to be checked for status
	 */
	private void checkAndUpdateGoal(Goal g){
		long today = new Date().getTime();
		if(g.getGoalDate()<= today && g.getStatus() != null && g.getStatus().equals(GoalStatus.ACTIVE)){
			Datapoint d = dpAdapter.getLastDatapoint(g.getId());
			if(d!=null){
				if(d.getValue() >= g.getGoalValue()){
					if( g.getPlannedDirection() ==1)
						g.setStatus(GoalStatus.ACHIEVED);
					else g.setStatus(GoalStatus.LOST);
				}else {
					if( g.getPlannedDirection() ==1)
						g.setStatus(GoalStatus.LOST);
					else g.setStatus(GoalStatus.ACHIEVED);
				}
			}else{
				g.setStatus(GoalStatus.LOST);
			}
			adapter.updateGoal(g, g.getId());
		}
	}
	/**
	 * get active goal for a measure id provided
	 * 
	 * @param username
	 * @param mid
	 * @return
	 */
	@GET
	@Path("{username}/{mId}/active")
	public Goal getActiveGoal(@PathParam("username")String username, @PathParam("mId")long mid){
		
		List<Goal> goals = adapter.getAll(username,mid);
		
		if(goals == null) return null;
		
		for(Goal g: goals){
			checkAndUpdateGoal(g);
			if(g.getStatus() !=null && g.getStatus().equals(GoalStatus.ACTIVE) ){
				return g;
			}
		}
		return null;
	}
	/**
	 * get list of achieved goals
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/achieved")
	public List<Goal> getAchievedGoals(@PathParam("username")String username){
		
		List<Goal> goals = adapter.getAll(username);
		List<Goal> actives = new ArrayList<>();
		
		if(goals == null) return new ArrayList<>();
		
		for(Goal g: goals){
			if(g.getStatus() !=null && g.getStatus().equals(GoalStatus.ACHIEVED) ){
				actives.add(g);
			}
		}
		return actives;
	}
	
	/**
	 * get list of canceled goals
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/canceled")
	public List<Goal> getCanceledGoals(@PathParam("username")String username){
		
		List<Goal> goals = adapter.getAll(username);
		List<Goal> actives = new ArrayList<>();
		
		if(goals == null) return new ArrayList<>();
		
		for(Goal g: goals){
			if(g.getStatus() !=null && g.getStatus().equals(GoalStatus.CANCELED) ){
				actives.add(g);
			}
		}
		return actives;
	}

	/**
	 * get list of lost goals
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/lost")
	public List<Goal> getLostGoals(@PathParam("username")String username){
		
		List<Goal> goals = adapter.getAll(username);
		List<Goal> actives = new ArrayList<>();
		
		if(goals == null) return new ArrayList<>();
		
		for(Goal g: goals){
			if(g.getStatus() !=null && g.getStatus().equals(GoalStatus.LOST) ){
				actives.add(g);
			}
		}
		return actives;
	}
		
	/**
	 * get all list of goals for a user, "Active", "Canceled", "Achieved", "Lost"
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/all")
	public List<Goal> getAll(@PathParam("username")String username){
		List<Goal> goals = adapter.getAll(username);
		for(Goal g: goals){
			checkAndUpdateGoal(g);
		}
		return goals;
	}
	
	/**
	 * get list of goals with measure 
	 * 
	 * @param username
	 * @param mid
	 * @return 
	 */
	@GET
	@Path("{username}/measure/{mid}")
	public List<Goal> getAll(@PathParam("username")String username, @PathParam("mid")long mid){
		List<Goal> goals = adapter.getAll(username, mid);
		for(Goal g: goals){
			checkAndUpdateGoal(g);
		}
		return goals;
	}
	
	/**
	 * get list of data point 
	 * 
	 * @param gId
	 * @return
	 */
	@GET
	@Path("{gid}/datapoints")
	public List<Datapoint> getDatapoints(@PathParam("gid")long gId){
		return dpAdapter.getGoalDatapoints(gId);
	}
	
	/**
	 *  add data point 
	 *  
	 * @param username
	 * @param gId
	 * @param datapoint
	 * @return
	 */
	@POST
	@Path("{username}/{gId}/datapoint.add")
	public Datapoint addDatapoint(@PathParam("username")String username, @PathParam("gId")long gId, Datapoint datapoint){
		
		Goal g = adapter.getGoal(gId);
		User u = userAdapter.getUser(username);
		
		if(g==null || u ==null) return null;
		if(g.getStatus()!=null && !g.getStatus().equals(GoalStatus.ACTIVE))
			return null;
		datapoint.setGoal(g);
		datapoint.setUpdatedTime(new Date().getTime());
		return dpAdapter.addDatapoint(datapoint);
	}
	
	/**
	 * update data point 
	 * 
	 * @param username
	 * @param gId
	 * @param datapoint
	 * @return
	 */
	@PUT
	@Path("{username}/{gId}/datapoint.update")
	public Datapoint updateDatapoint(@PathParam("username")String username, @PathParam("gId")long gId, Datapoint datapoint){
		
		Goal g = adapter.getGoal(gId);
		User u = userAdapter.getUser(username);
		
		if(datapoint == null) return null;
		
		Datapoint dp = dpAdapter.getDatapoint(datapoint.getId());
		
		if(g==null || u ==null|| dp == null || !u.equals(g.getUser())) return null;
		
		if(g.getStatus()!=null && !g.getStatus().equals(GoalStatus.ACTIVE))
			return null;
		
		datapoint.setGoal(g);
		datapoint.setUpdatedTime(new Date().getTime());
		
		return dpAdapter.updateDatapoint(datapoint, gId);
	}
	
	/**
	 * delete data point of goal
	 * 
	 * @param username
	 * @param gId
	 * @param dpId
	 * @return
	 */
	@DELETE
	@Path("{username}/{gId}/{dpId}/datapoint.del")
	public String deleteDatapoint(@PathParam("username")String username, @PathParam("gId")long gId,@PathParam("dpId")long dpId){
	
		Goal g = adapter.getGoal(gId);
		User u = userAdapter.getUser(username);
		
		Datapoint dp = dpAdapter.getDatapoint(dpId);
		
		if(g==null || u ==null|| dp == null || !u.equals(g.getUser())) return null;
		
		if(g.getStatus()!=null && !g.getStatus().equals(GoalStatus.ACTIVE))
			return null;
		
		return dpAdapter.deleteDatapoint(dpId);
	}
	
	
}
