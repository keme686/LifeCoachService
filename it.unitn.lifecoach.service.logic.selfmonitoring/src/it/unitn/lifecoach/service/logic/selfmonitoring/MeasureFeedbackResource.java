/**
 * 
 */
package it.unitn.lifecoach.service.logic.selfmonitoring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.storage.local.adapter.DatapointDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.MeasureDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.UserDataAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/measurefeedbacklogic")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class MeasureFeedbackResource {

	/**
	 * Measure data adapter which users jersey client adapter to call the Data storage REST service
	 */
	MeasureDataAdapter adapter = new MeasureDataAdapter();
	/**
	 * Datapoint data adapter which users jersey client adapter to call the Data storage REST service
	 */
	DatapointDataAdapter dpAdapter = new DatapointDataAdapter();
	/**
	 * User data adapter which users jersey client adapter to call the Data storage REST service
	 */
	UserDataAdapter userAdapter = new UserDataAdapter();
	

	/**
	 * get body mass index - BMI of a user 
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/bmi")
	public Response getBmi(@PathParam("username")String username){
		List<Measure> measures = adapter.getAll(username);		
		Measure weight = null;
		Measure height = null;
		for(Measure m: measures){
			if(m.getName().equalsIgnoreCase("weight"))
				weight  = m;
			if(m.getName().equalsIgnoreCase("height"))
				height = m;
		}
		if(weight == null || height == null){
			String bmi = "No height or/and weight data available!";
			return Response.ok(bmi).build();
		}
		double bmi =-1.0;
		if(height.getValue() > 0)			
		 bmi = weight.getValue() / (Math.pow(height.getValue(), 2));
		 String res = bmi+"";
		return Response.ok(res).build();
	}
	
	/**
	 * get list of measure that a user should be reminded
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/dfreminder/daily")
	public List<Measure> getDailyDataFeedReminder(@PathParam("username")String username){
		Calendar today = Calendar.getInstance();
		List<Measure> measures = adapter.getAll(username);
		List<Measure> missing = new ArrayList<>();
		
		for(Measure m: measures){							
			Calendar stamp = Calendar.getInstance();
			stamp.setTimeInMillis(m.getUpdateTime());
			if(stamp.get(Calendar.DATE) != today.get(Calendar.DATE) 
					|| stamp.get(Calendar.MONTH) != today.get(Calendar.MONTH) 
					|| stamp.get(Calendar.YEAR) != today.get(Calendar.YEAR)){
				missing.add(m);
			}
		}
		
		return missing;
	}
	/**
	 * get measure info if it is not inserted today, cool otherwise
	 * 
	 * @param username
	 * @param mid
	 * @return
	 */
	@GET
	@PathParam("{username}/dfreminder/{mid}")
	public Response getDataFeedReminderForMeasure(@PathParam("username")String username, @PathParam("mid")long mid){
		Calendar today = Calendar.getInstance();
		Measure m = adapter.getMeasure(mid);
		User u = userAdapter.getUser(username);
		if(!u.equals(m.getUser())){
			String response = "Invalid user-measure combination";
			return Response.ok(response).build();
		}
		Calendar stamp = Calendar.getInstance();
		stamp.setTimeInMillis(m.getUpdateTime());
		if(stamp.get(Calendar.DATE) != today.get(Calendar.DATE) 
				|| stamp.get(Calendar.MONTH) != today.get(Calendar.MONTH) 
				|| stamp.get(Calendar.YEAR) != today.get(Calendar.YEAR)){
			return Response.ok(m).build();
		}
		String response = "Cool";
		return Response.ok(response).build();
	}
	
	/**
	 * get list of measure that has been entered more than a week ago
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/dfreminder/b4week")
	public List<Measure> getB4WeekDataFeedReminder(@PathParam("username")String username){
		Calendar today = Calendar.getInstance();
		List<Measure> measures = adapter.getAll(username);
		List<Measure> missing = new ArrayList<>();
		
		for(Measure m: measures){							
			Calendar stamp = Calendar.getInstance();
			stamp.setTimeInMillis(m.getUpdateTime());
			if(stamp.get(Calendar.DATE) <= (today.get(Calendar.DATE)-6) 
					|| stamp.get(Calendar.MONTH) <= (today.get(Calendar.MONTH)-6) 
					|| stamp.get(Calendar.YEAR) <= (today.get(Calendar.YEAR)-6)){
				missing.add(m);
			}
		}
		
		return missing;
	}
	/**
	 * get list of measure that has not been entered since last week
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/dfreminder/week")
	public List<Measure> getWeeklyDataFeedReminder(@PathParam("username")String username){
		Calendar today = Calendar.getInstance();
		List<Measure> measures = adapter.getAll(username);
		List<Measure> missing = new ArrayList<>();
		
		for(Measure m: measures){							
			Calendar stamp = Calendar.getInstance();
			stamp.setTimeInMillis(m.getUpdateTime());
			if((stamp.get(Calendar.DATE)+6) >= (today.get(Calendar.DATE)) 
					|| (stamp.get(Calendar.MONTH)+6) >= (today.get(Calendar.MONTH)) 
					|| (stamp.get(Calendar.YEAR)+6) >= (today.get(Calendar.YEAR))){
				missing.add(m);
			}
		}
		
		return missing;
	}
	/**
	 * get all missing data feeds till today
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/dfreminder/allmissing")
	public List<Measure> getAllMissingDataFeedReminder(@PathParam("username")String username){
		Calendar today = Calendar.getInstance();
		List<Measure> measures = adapter.getAll(username);
		List<Measure> missing = new ArrayList<>();
		
		for(Measure m: measures){							
			Calendar stamp = Calendar.getInstance();
			stamp.setTimeInMillis(m.getUpdateTime());
			if(stamp.get(Calendar.DATE) <= (today.get(Calendar.DATE)) 
					|| stamp.get(Calendar.MONTH) <= (today.get(Calendar.MONTH)) 
					|| stamp.get(Calendar.YEAR) <= (today.get(Calendar.YEAR))){
				missing.add(m);
			}
		}
		
		return missing;
	}
	/**
	 * gets a sum of measure values entered so far for a measure within a number of entries specified
	 * 
	 * @param username
	 * @param mid
	 * @param days
	 * @return
	 */
	@GET
	@Path("{username}/total/{mid}/{numofentries}")
	public Response getTotalValue(@PathParam("username")String username, @PathParam("mid")long mid, @PathParam("numofentries")int days){
		//Calendar today = Calendar.getInstance();
		double total =0.0;
		List<Datapoint> history = dpAdapter.getAll(mid);
		int i = 0;
		for(Datapoint m: history){		
			Calendar stamp = Calendar.getInstance();
			stamp.setTimeInMillis(m.getTimestamp());
			if(i<days || days <= 0){
				//if(stamp.get(Calendar.DATE) <= (today.get(Calendar.DATE)) 
				//		&& stamp.get(Calendar.MONTH) <= (today.get(Calendar.MONTH)) 
					//	&& stamp.get(Calendar.YEAR) <= (today.get(Calendar.YEAR))){
					total+=m.getValue();
				//}
			}else
				break;
			
			i++;
		}
		
		return Response.ok(total).build();
	}
	/**
	 * gets the average value of a measure of a user for specified number of entries
	 * 
	 * @param username
	 * @param mid
	 * @param days
	 * @return
	 */
	@GET
	@Path("{username}/average/{mid}/{numofentries}")
	public Response getAverageValue(@PathParam("username")String username, @PathParam("mid")long mid, @PathParam("numofentries")int days){
		//Calendar today = Calendar.getInstance();
		double total =0.0;
		List<Datapoint> history = dpAdapter.getAll(mid);
		int i = 0;
		for(Datapoint m: history){		
			Calendar stamp = Calendar.getInstance();
			stamp.setTimeInMillis(m.getTimestamp());
			if(i<days || days <= 0){
				//if(stamp.get(Calendar.DATE) <= (today.get(Calendar.DATE)) 
				//		&& stamp.get(Calendar.MONTH) <= (today.get(Calendar.MONTH)) 
					//	&& stamp.get(Calendar.YEAR) <= (today.get(Calendar.YEAR))){
					total+=m.getValue();
				//}
			}else
				break;
			
			i++;
		}
		if(days<=0 && i > 0)
			total = total/i;
		else if(days > 0)
			total = total/days;
		
		return Response.ok(total).build();
	}
	
	
}
