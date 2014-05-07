/**
 * 
 */
package it.unitn.lifecoach.service.process.selfmonitoring;

import java.util.List;

import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.service.logic.selfmonitoring.adapter.MeasureFeedbackLogicAdapter;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class is used to get feedback on {@link Measure} related information of a user
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/measurefeedback")

@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class MeasureFeedbackProcessResource {

	/**
	 * Adapter for self monitoring measure logic service
	 */
	private MeasureFeedbackLogicAdapter adapter = new MeasureFeedbackLogicAdapter();
	/**
	 * Adapter for authentication service
	 */
	private UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();
	
	/**
	 * Get body mass index - BMI of a user 
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if user with specified id doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with BMI information<br /> 
	 */
	@GET
	@Path("{username}/bmi")
	public Response getBmi(@PathParam("username")String username, @QueryParam("access_token")String token){		
		if(username == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		String bmi = adapter.getBmi(username);				
		return Response.ok(bmi).build();
	}
	
	/**
	 * Get list of measure that a user should be reminded on daily basis 
	 *  
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if user with specified id doesnt exist<br />
	 * 				Response 204 if user token is not valid<br />
	 * 				Response 200 with {@link List} of {@link Measure} information<br /> 
	 */
	@GET
	@Path("{username}/dfreminder/daily")
	public List<Measure> getDailyDataFeedReminder(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		
		return adapter.getDailyDataFeedReminder(username);
	}
	
	/**
	 * Get measure info if it is not inserted today, cool otherwise
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param mid 	id of a measure
	 * @return		Response 204 if parameter value is null or if user/measure with specified id doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with data insertion information<br /> 
	 */
	@GET
	@PathParam("{username}/dfreminder/{mid}")
	public Response getDataFeedReminderForMeasure(@PathParam("username")String username, @PathParam("mid")long mid, @QueryParam("access_token")String token){
		if(mid ==0  || username == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		String response = adapter.getDataFeedReminderForMeasure(username, mid);
		return Response.ok(response).build();
	}
	
	/**
	 * Get list of measure that has been entered more than a week ago
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if user/measure with specified id doesnt exist<br />
	 * 				Response 204 if user token is not valid<br />
	 * 				Response 200 with data insertion information<br /> 
	 */
	@GET
	@Path("{username}/dfreminder/b4week")
	public List<Measure> getB4WeekDataFeedReminder(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getB4WeekDataFeedReminder(username);
	}
	
	/**
	 * Get list of measure that has not been entered since last week
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if user/measure with specified id doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link List} of {@link Measure} information<br /> 
	 */
	@GET
	@Path("{username}/dfreminder/week")
	public List<Measure> getWeeklyDataFeedReminder(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getWeeklyDataFeedReminder(username);
	}
	
	/**
	 * Get all missing data feeds till today
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if user/measure with specified id doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link List} of {@link Measure} information<br /> 
	 */
	@GET
	@Path("{username}/dfreminder/allmissing")
	public List<Measure> getAllMissingDataFeedReminder(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getAllMissingDataFeedReminder(username);
	}
	
	/**
	 * Gets a sum of measure values entered so far for a measure within a number of entries specified
	 *  
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param mid  id of a measure
	 * @param days number of days to get total value of a measure
	 * @return		Response 204 if parameter value is null or if user/measure with specified id doesn't exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with sum of measure values form {@link Datapoint} information<br /> 
	 */
	@GET
	@Path("{username}/total/{mid}/{numofentries}")
	public Response getTotalValue(@PathParam("username")String username, @PathParam("mid")long mid, @PathParam("numofentries")int days, @QueryParam("access_token")String token){
		if(username == null || mid == 0 || days<=0 || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		double total = adapter.getTotalValue(username, mid, days);
		return Response.ok(total+"").build();
	}
	
	/**
	 * Gets the average value of a measure of a user for specified number of entries
	 * 
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param mid	id of a measure
	 * @param days number of days to get average measure data value
	 * @return		Response 204 if parameter value is null or if user/measure with specified id doesn't exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with average value of measure data form {@link Datapoint} information<br /> 
	 */
	@GET
	@Path("{username}/average/{mid}/{numofentries}")
	public Response getAverageValue(@PathParam("username")String username, @PathParam("mid")long mid, @PathParam("numofentries")int days, @QueryParam("access_token")String token){
		if(username == null || mid ==0 || days<=0 || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		
		double average = adapter.getAverageValue(username, mid, days);	
		return Response.ok(average+"").build();
	}
	
}
