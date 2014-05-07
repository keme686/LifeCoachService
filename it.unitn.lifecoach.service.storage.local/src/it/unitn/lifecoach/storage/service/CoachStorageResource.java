/**
 * 
 */
package it.unitn.lifecoach.storage.service;

import java.util.List;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.model.MealLog;
import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.storage.dao.DatapointDAO;
import it.unitn.lifecoach.storage.dao.GoalDAO;
import it.unitn.lifecoach.storage.dao.MealLogDAO;
import it.unitn.lifecoach.storage.dao.MeasureDAO;
import it.unitn.lifecoach.storage.dao.UserDAO;

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
 * 
 * @author kemele
 *
 */
@Path("/coachdata")
public class CoachStorageResource {
	
	private UserDAO userdao = new UserDAO();
	private MeasureDAO msdao = new MeasureDAO();
	private MealLogDAO mlogdao = new MealLogDAO();
	private GoalDAO goaldao = new GoalDAO();
	private DatapointDAO dpdao = new DatapointDAO();
	
	

	//////////////////////////////////////////////////
	///////////// USER /////////////////////////////
	////////////////////////////////////////////////
	/**
	 * add a new user to the database 
	 * 
	 * @param user
	 * @return the user added, null otherwise 
	 */
	@POST
	@Path("user/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("user/{username}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("user/{username}/del")
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("user/{username}")
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("user/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers(){
		try{
			return userdao.getAll();
		}catch(Exception e){
			return null;
		}
	}
	
	//////////////////////////////////////////////////////////
	////////////// MEASURE ///////////////////////////////////
	/////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param measure
	 * @return
	 */
	@POST
	@Path("measure/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Measure addMeasure(Measure measure){
		try{
			measure = msdao.addMeasure(measure);
			return measure;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param mid
	 * @param measure
	 * @return
	 */
	@PUT
	@Path("measure/{mid}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Measure updateMeasure(@PathParam("mid")long mid, Measure measure){
		try{
			measure = msdao.update(measure);
			return measure;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param mid
	 * @return
	 */
	@DELETE
	@Path("measure/{mid}/del")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteMeasure(@PathParam("mid")long mid){
		try{
			boolean st = msdao.delete(mid);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting measure\"}";
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("measure/{mid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Measure getMeasure(@PathParam("mid")long id){
		try{
			return msdao.get(id);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("measure/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Measure> getAllMeasures(){
		try{
			return msdao.getAll();
		}catch(Exception e){
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
	@Path("measure/{username}/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Measure> getAllMeasures(@PathParam("username")String username){
		try{
			return msdao.getAll(username);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/////////////////////////////////////////////////
	///////////////// GOAL //////////////////////////
	/////////////////////////////////////////////////
	
	/**
	 * 
	 * @param goal
	 * @return
	 */
	@POST
	@Path("goal/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("goal/{goalId}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("goal/{goalId}/del")
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("goal/{goalId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Goal getGoal(@PathParam("goalId")long goalId){
		try{
			return goaldao.get(goalId);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("goal/{username}/all")
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("goal/{username}/{measureId}")
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("goal/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Goal> addAllGoals(){
		try{
			return goaldao.getAll();
		}catch(Exception e){
			return null;
		}
	}
	
	//////////////////////////////////////////////////
	///////////////// MEAL LOG //////////////////////
	/////////////////////////////////////////////////
	/**
	 * 
	 * @param log
	 * @return
	 */
	@POST
	@Path("meal/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MealLog addMeal(MealLog log){
		try{
			return mlogdao.addMealLog(log);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param mid
	 * @param log
	 * @return
	 */
	@PUT
	@Path("meal/{mid}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MealLog updateMeal(@PathParam("mid")long mid, MealLog log){
		try{
			return mlogdao.update(log);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param mid
	 * @return
	 */
	@DELETE
	@Path("meal/{mid}/del")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteMeal(@PathParam("mid")long mid){
		try{
			boolean st = mlogdao.delete(mid);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting Meal Log\"}";
		}
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("meal/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MealLog> getMealLogs(@PathParam("username")String username){
		try{
			return mlogdao.getAll(username);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("meal/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MealLog> getAllMealLogs(){
		try{
			return mlogdao.getAll();
		}catch(Exception e){
			return null;
		}
	}
	
	//////////////////////////////////////
	///////////// DATAPOINT //////////////
	//////////////////////////////////////
	/**
	 * 
	 * @param dp
	 * @return
	 */
	@POST
	@Path("datapoint/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Datapoint addDatapoint(Datapoint dp){
		try{
			return dpdao.add(dp);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param dpId
	 * @param dp
	 * @return
	 */
	@PUT
	@Path("datapoint/{dpId}/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Datapoint updateDatapoint(@PathParam("dpId")long dpId, Datapoint dp){
		try{
			return dpdao.update(dp);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param dpId
	 * @return
	 */
	@DELETE
	@Path("datapoint/{dpId}/del")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteDatapoint(@PathParam("dpId")long dpId){
		try{
			boolean st = dpdao.delete(dpId);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting Datapoint\"}";
		}
	}
	/**
	 * 
	 * @param dpId
	 * @return
	 */
	@GET
	@Path("datapoint/{dpId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Datapoint getDatapoint(@PathParam("dpId")long dpId){
		try{
			return dpdao.get(dpId);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param measureId
	 * @return
	 */
	@GET
	@Path("datapoint/{measureId}/measure")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Datapoint> getMeasureDatapoints(@PathParam("measureId")long measureId){
		try{
			return dpdao.getAllMeasureDatapoints(measureId);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param goalId
	 * @return
	 */
	@GET
	@Path("datapoint/{goalId}/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Datapoint> getGoalDatapoints(@PathParam("goalid")long goalId){
		try{
			return dpdao.getAllGoalDatapoints(goalId);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("datapoint/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Datapoint> getAllDatapoints(){
		try{
			return dpdao.getAll();
		}catch(Exception e){
			return null;
		}
	}
}
