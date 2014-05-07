package it.unitn.lifecoach.service.logic.meallog;

import it.unitn.lifecoach.model.GraphPoint;
import it.unitn.lifecoach.model.MealLog;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.storage.local.adapter.MealDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.UserDataAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.joda.time.DateTime;
import org.joda.time.Interval;


/**
 * Adapter 
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/meallogic")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class MealLogicResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	/**
	 * User data adapter which users jersey client adapter to call the Data storage REST service
	 */
	UserDataAdapter userAdapter = new UserDataAdapter();

	
	/**
	 *  Meal data adapter to communicate with Meal data resource in Data Storage service
	 */
	private MealDataAdapter mealAdapter = new MealDataAdapter();

	/**
	 * Get all the meal logged about a user during a period specified by the parameters from and to.
	 *  
	 * @param from_date
	 * @param to_date
	 * @param username
	 * @return
	 */
	@GET
	@Path("interval")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<MealLog> getMealLogsBetweenInterval(@QueryParam("from") String from_date,
			@QueryParam("to") String to_date, @QueryParam("username") String username) {
		
			long from =Long.parseLong(from_date);
			long to =Long.parseLong(to_date);
			if(Long.parseLong(from_date)>Long.parseLong(to_date))
				return null;
			
			User user = userAdapter.getUser(username);
			if(user==null)
				return null;
			
			List<MealLog> list = mealAdapter.getAll(username);
			List<MealLog> response = new ArrayList<MealLog>();
			
			//Gets the logs within the interval
			if(list!=null){
				Iterator<MealLog> itr = list.iterator();
				while (itr.hasNext()){		
					MealLog log = new MealLog();
					log=itr.next();
					Long updTime =  log.getUpdatedTime();
					if(updTime>=from && updTime<=to){
						response.add(log);
					}
				}
			}else{
				return null;
			}		
			return response;		
	}
	
	/**
	 * Get all the logs for the specified date
	 * @param datetime
	 * @param username
	 * @return
	 */
	@GET
	@Path("day")
	public List<MealLog> getMealLogsOfDay(@QueryParam("date") String datetime, @QueryParam("username") String username) {
		
			User user = userAdapter.getUser(username);
			if(user==null)
				return null;
			
			List<MealLog> list = mealAdapter.getAll(username);
			List<MealLog> response = new ArrayList<MealLog>();
			
			DateTime dt = new DateTime(Long.parseLong(datetime));
			long startOfDay = dt.withTimeAtStartOfDay().getMillis();
			long endOfDay = startOfDay+(1000*60*60*24);
			
			//Get the logs within the day
			if(list!=null){
				Iterator<MealLog> itr = list.iterator();
				while (itr.hasNext()){		
					MealLog log = new MealLog();
					log=itr.next();
					Long updTime =  log.getUpdatedTime();
					if(updTime>=startOfDay && updTime<=endOfDay){
						response.add(log);
					}
				}
			}else{
				return null;
			}		
			return response;		
	}
	
	/**
	 * Return a list of points.A point represents the sum of calories consumed that day
	 * 
	 * @param from_date
	 * @param to_date
	 * @param username
	 * @return
	 */
	@GET
	@Path("interval/cal")
	public List<GraphPoint> getCaloriesForInterval(@QueryParam("from") String from_date,
			@QueryParam("to") String to_date, @QueryParam("username") String username) {
		
			
			if(Long.parseLong(from_date)>Long.parseLong(to_date)){
				return null;
			}
				
			User user = userAdapter.getUser(username);			
			if(user==null){
				return null;
			}
			
			//Get all the meals logs for the user
			List<MealLog> list = mealAdapter.getAll(username);
			if(list==null){
				return null;
			}
			
			//Create DateTime object for start and end date of the interval
			DateTime start = new DateTime(Long.parseLong(from_date));
			DateTime end = new DateTime(Long.parseLong(to_date));
			
			//Create a new TreeSet that will sort the meals according to the updated time
			MealLogTimeComparator mltc = new MealLogTimeComparator();
			TreeSet<MealLog> mealSet= new TreeSet<MealLog>(mltc);
			mealSet.addAll(list);
				
			//Empty MealLog object where updated date is set as the beginning of the day at the start date
			MealLog startLog = new MealLog();
			startLog .setUpdatedTime(start.withTimeAtStartOfDay().getMillis());

			//Get the first meal log within the set that is more than the starting date. 
			startLog = mealSet.ceiling(startLog);
			
			//If the does not exist any log after starting date
			if(startLog ==null){
				return null;
			}
			
			//Empty MealLog object where updated date is set as the ending of the day at the end date
			MealLog endLog = new MealLog();
			endLog .setUpdatedTime(end.withTimeAtStartOfDay().plus(1000*60*60*24).getMillis());
			
			//Get the last meal log within the set that is less than the end date.
			MealLog endLogFloor = mealSet.floor(endLog);
			
			//Store the points in this list. One point per day.
			List<GraphPoint> points_list = new ArrayList<GraphPoint>();
			
			//If they match there exist only one log within the interval 
			if(endLogFloor.getUpdatedTime()==startLog.getUpdatedTime()){
				points_list.add(new GraphPoint((new DateTime(startLog.getUpdatedTime())).withTimeAtStartOfDay().getMillis()+"",
						""+startLog.getCalories()));
				return points_list;
			}
			
			//From all the logs of the user retrieve only those within the interval
			SortedSet<MealLog> logsWithinInterval;
			logsWithinInterval= mealSet.subSet(startLog, true, endLogFloor, true ); 
			
			
			//Initialize startOfDay and endOfDay to represent respectively the beginning and ending of the start date of the interval
			long startOfDay = start.withTimeAtStartOfDay().getMillis();
			long endOfDay = start.withTimeAtStartOfDay().plus(1000*60*60*24).getMillis();
			
			//Represents a full day. Initially initialized as the start date of the interval
			Interval day= new Interval(startOfDay,endOfDay);
			
			//Find the sum of calories consumed each day. 
			Iterator<MealLog> itr = logsWithinInterval.iterator();
			int sumCal=0;
			int counter=0;
			while (itr.hasNext()){
				
				MealLog ml = itr.next();
				counter++;
				//until this condition is true, sum the calories for the day
				if(day.contains(ml.getUpdatedTime())){
					sumCal=sumCal+ml.getCalories();
				}else{
					//when the previous condition fails, create a new point 
					//which stores the total number of calories consumed within the day.
					//Add the point to the list of graph points
					points_list.add(new GraphPoint(""+startOfDay, ""+sumCal));
					
					//push the interval to the next day 
					//1000*60*60*24 is a full day in milliseconds
					startOfDay=endOfDay;
					endOfDay= endOfDay+(1000*60*60*24);
					day=new Interval(startOfDay,endOfDay);
					// store the value of the meal log that failed to pass the condition 
					sumCal=ml.getCalories();
				}
				
				if (counter==logsWithinInterval.size()){ // The logs in the end of the list will never fail the if condition above so we need to handle them here
					points_list.add(new GraphPoint(""+startOfDay,""+sumCal));
				}
			}
			return points_list;				
	}
	
	/**
	 * Return the sum of calories for the requested date
	 * @param datetime
	 * @param username
	 * @return
	 */
	@GET
	@Path("day/cal")
	public GraphPoint getCaloriesOfDay(@QueryParam("date") String datetime, 
			@QueryParam("username") String username) {
		
		//Check if the user exists
		User user = userAdapter.getUser(username);
		if(user==null){
			System.out.println("user is null");
			return null;
		}
		
		//Get all the meals logs for the user
		List<MealLog> list = mealAdapter.getAll(username);
		if(list==null){
			System.out.println("list is null");
			return null;
		}
		
		//Start of the day in milliseconds according to the UNIX time
		DateTime dt=  new  DateTime(Long.parseLong(datetime));
		long startOfDay=dt.withTimeAtStartOfDay().getMillis(); 
		
		//Create a new TreeSet that will sort the meals according to the updated time
		MealLogTimeComparator mltc = new MealLogTimeComparator();
		TreeSet<MealLog> mealSet= new TreeSet<MealLog>(mltc);
		mealSet.addAll(list);
			
		//Empty MealLog object where updated date is set as the beginning of the day
		MealLog startLog = new MealLog();
		startLog .setUpdatedTime(startOfDay);

		//Get the first meal log within the set that is more than the beginning of the day 
		startLog = mealSet.ceiling(startLog);
		
		//If there does not exist any log after the beginning of the day
		if(startLog ==null){
			System.out.println("start log is log");
			return null;
		}
		
		//Empty MealLog object where updated date is set as the ending of the day
		MealLog endLog = new MealLog();
		endLog .setUpdatedTime(startOfDay+(1000*60*60*24));
		
		//Get the last (greatest) meal log within the set that is less than the end of the day
		MealLog endLogFloor = mealSet.floor(endLog);
				
		//If they match there exist only one log within the day 
		if(endLogFloor.getUpdatedTime()==startLog.getUpdatedTime()){
			return new GraphPoint(""+startOfDay,""+startLog.getCalories());
		}
				
		//From all the logs of the user retrieve only those within the day
		SortedSet<MealLog> logsWithinInterval;
		logsWithinInterval= mealSet.subSet(startLog, true, endLogFloor, true ); 
		
		//Find the sum of the calories for today.
		int sumCal=0;
		Iterator<MealLog> itr = logsWithinInterval.iterator();
		while (itr.hasNext()){		
			MealLog ml = new MealLog();
			ml = itr.next();
			sumCal=sumCal+ml.getCalories();
		}
		GraphPoint gp= new GraphPoint(""+startOfDay,""+sumCal);
		return gp;
	}
	
	/**
	 * Add a new meal to the log
	 * @param log
	 * @param username
	 * @return
	 */
	@POST
	@Path("{username}/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MealLog addMealLog( MealLog log, @PathParam("username") String username) {
		User user = userAdapter.getUser(username);
		if(user==null)
			return null;
		log.setUser(user);
		log.setUpdatedTime(new Date().getTime());
		
		MealLog response = mealAdapter.addMealLog(log);		
		return response;
	}

	/**
	 * Update existing meal record
	 * 
	 * @param username
	 * @param log
	 * @param mid
	 * @return
	 */
	@PUT
	@Path("{username}/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MealLog updateMealLog(MealLog log, @PathParam("username") String username, @PathParam("id") long mid) {

		User user = userAdapter.getUser(username);
		if(user==null)
			return null;
		log.setUser(user);
		log.setUpdatedTime(new Date().getTime());
		return mealAdapter.updateMealLog(log, mid);
	}

	/**
	 * Delete a meal record from the log
	 * 
	 * @param username
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{username}/delete/{id}")
	public String deleteLog(@PathParam("id") long id, @PathParam("username") String username) {
		
		User user = userAdapter.getUser(username);
		if(user==null)
			return "User does not exists";
		
		return  mealAdapter.deleteMealLog(id);
	}

	/**
	 * Used to sort the logs based on the updated date 
	 * 
	 * @author Elvis Koci <elvkoci@gmail.com>
	 *
	 */
 	public class MealLogTimeComparator implements Comparator<MealLog>{

		@Override
		public int compare(MealLog a, MealLog b) {
			return Long.compare(a.getUpdatedTime(), b.getUpdatedTime());
		}
	
 	}
 	
 	@GET
	@Path("{username}")
 	public List<MealLog> getMealLogs(@PathParam("username")String username){
 		return mealAdapter.getAll(username);
 	}
	
}
