/**
 * 
 */
package it.unitn.lifecoach.service.logic.goaltracking;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.service.storage.local.adapter.DatapointDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.GoalDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.UserDataAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/goalfeedbacklogic")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class GoalFeedbackResource {

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
	 * get expected data value by a specified date
	 * 
	 * @param date - date for expected data
	 * @param gId - goal id
	 * @return	expected value of current datapoint by this date
	 * 			204 -if goal doesn't exist 
	 */
	@GET
	@Path("expected")
	public Response getExpectedGoalValue(@QueryParam("date") long date, @QueryParam("gid")long gId){
		
		Goal g = adapter.getGoal(gId);
		if(g == null){
			return Response.noContent().build();
		}
		double init = g.getInitialValue();
		double rate = g.getRate();
		
		long mils = date - g.getUpdatedTime();		
		//number of days since goal set
		long days = mils / (24*3600*1000);
		//expected
		double exp = init + rate*days;
		return Response.ok(exp+"").build();
	}
	/**
	 * get feedback for a goal at the specified date
	 * 
	 * @param date
	 * @param gId
	 * @return		0 - Doing as planned
	 * 				1 - Doing more than planned, Better
	 * 				-1 - Doing under planned rate 
	 */
	@GET
	@Path("feedback")
	public Response getFeedback(@QueryParam("date") long date, @QueryParam("gid")long gId){
		Goal g = adapter.getGoal(gId);
		if(g == null){
			return Response.noContent().build();
		}
		double init = g.getInitialValue();
		double rate = g.getRate();
				
		long mils = date - g.getUpdatedTime();			
		long days = mils / (24*3600*1000);
		Datapoint d = dpAdapter.getLastDatapoint(gId);
		if(d == null){
			return Response.ok(0+"").build();
		}
		
		double exp = init + rate*days;
		double r = exp-d.getValue();
		 if(r > 0){
			if(g.getPlannedDirection()  == 1){   
				return Response.ok(-1+"").build();  //planned to increase but decreasing
			}else{
				return Response.ok(1+"").build();
			}
		}else if(r < 0){
			if(g.getPlannedDirection()  == 1){
				return Response.ok(1+"").build(); ////planned to increase so true
			}else{
				return Response.ok(-1+"").build(); //planned to decrease but increasing
			}
		}else {
			return Response.ok(0+"").build();
		}
	}
	/**
	 * get progress report for specified goal
	 * 
	 * @param date - progress checking date
	 * @param gId - goal id
	 * @return -2 Red - means needs more than 160% rate to achieve goal from initial rate estimated
	 * 			-1 Yellow - means needs up to 160% rate to achieve a goal
	 * 			0  Green - means progress on track 
	 * 			Response 204 - if goal doesn't exist
	 */
	@GET
	@Path("progress")
	public Response getProgress(@QueryParam("date") long date, @QueryParam("gid")long gId){
		Goal g = adapter.getGoal(gId);
		if(g == null){
			return Response.noContent().build();
		}	
		Datapoint d = dpAdapter.getLastDatapoint(gId);
		if(d == null){
			return Response.ok(0+"").build();
		}
		
		double left = g.getGoalValue() - d.getValue();
		long leftdate = (g.getGoalDate()-date)/(24*3600*1000);
		double newRate = left / leftdate;
		
		double ratio = newRate/g.getRate();
		
		
		if(ratio<0)
			ratio *=-1;
		
		if(ratio >=1.6){
			return Response.ok(-2+"").build(); // Red - means needs more than 160% rate to achieve goal from initial rate estimated
		}else if(ratio > 1){
			return Response.ok(-1+"").build(); // Yellow - means needs up to 160% rate to achieve a goal
		}else{
			return Response.ok(0+"").build(); // Green - means progress on track 
		}	
	}
}
