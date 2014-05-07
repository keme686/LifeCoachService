/**
 * 
 */
package it.unitn.lifecoach.service.process.goaltracking.adapter;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Goal;

import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class GoalTrackingProcessAdapter {

	/**
	 * 
	 */
	WebResource service;
	/**
	 * 
	 */
	public GoalTrackingProcessAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	/**
	 * 
	 * @param goal
	 * @param username
	 * @param token
	 * @return goal information if successful, Response 401 if user not authenticated, or Response 204 otherwise
	 */
	public Goal addGoal(Goal goal, String username, String token){
		try {
			Goal u = service.path("goaltracking").path(username).queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Goal.class, goal);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(" addGoal(Goal goal, String username) process: " + response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param gId
	 * @param token
	 * @return goal
	 */
	public Goal getGoal(String username,long gId, String token){
		try {
			Goal u = service.path("goaltracking").path(username).path(gId+"").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(Goal.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getGoal(String username,long gId) process: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param goal
	 * @param username
	 * @param gId
	 * @param token
	 * @return goal
	 */
	public Goal updateGoal(Goal goal, String username, long gId, String token){
		try{
			Goal u = service.path("goaltracking").path(username).path(""+gId).queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Goal.class, goal);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("updateGoal(Goal goal, String username, long gId) process: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param gId
	 * @param token
	 * @return goal
	 */
	public Goal cancelGoal(String username, long gId, String token){
		try{
			Goal u = service.path("goaltracking").path(username).path(""+gId).queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete(Goal.class);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("cancelGoal(String username, long gId) process: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param token
	 * @return list of goal
	 */
	public List<Goal> getActiveGoals(String username, String token){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaltracking").path(username).path("active").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("process getActiveGoals(String username): "+ response.getStatus());
			 return null;
		 }catch(Exception e){
			 System.out.println("process getActiveGoals(String username) general exception: " + e.getMessage());
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param mid
	 * @param token
	 * @return goal
	 */
	public Goal getActiveGoal(String username, long mid, String token){
		try{
			Goal u = service.path("goaltracking").path(username).path(""+mid).path("active").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(Goal.class);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param token
	 * @return list of goal
	 */
	public List<Goal> getCanceledGoals(String username, String token){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaltracking").path(username).path("canceled").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param token
	 * @return list of goal
	 */
	public List<Goal> getAll(String username, String token){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaltracking").path(username).path("all").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param token
	 * @return list of goal
	 */
	public List<Goal> getAchievedGoals(String username, String token){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaltracking").path(username).path("achieved").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param token
	 * @return list of goal
	 */
	public List<Goal> getLostGoals(String username, String token){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaltracking").path(username).path("lost").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param mid
	 * @param token
	 * @return list of goal
	 */
	public List<Goal> getAll(String username, long mid, String token){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goaltracking").path(username).path(""+mid).queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("goal tracking getAll(String username, long mid) process: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param gId
	 * @param token
	 * @param usename
	 * @return list of datapoint
	 */
	public List<Datapoint> getDatapoints(long gId, String token, String usename){
		try{
			GenericType<List<Datapoint>> genericListType =  new GenericType<List<Datapoint>>() {};	                
			List<Datapoint> u = service.path("goaltracking").path(gId+"").path("datapoints").queryParam("username", usename).queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
			Collections.sort(u, new Comparator<Datapoint>() {
				@Override
			    public int compare(Datapoint c1, Datapoint c2) {
					return  (int)( c1.getTimestamp() - c2.getTimestamp());
			    }
			});
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getDatapoints(long gId) process: "+ response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param gid
	 * @param dp
	 * @param token
	 * @return datapoint
	 */
	public Datapoint addDatapoint(String username, long gid, Datapoint dp, String token){
		try {
			Datapoint u = service.path("goaltracking").path(username).path(""+gid).path("datapoint.add").queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Datapoint.class, dp);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("addDatapoint(String username, long gid, Datapoint dp) process"+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param gid
	 * @param dp
	 * @param token
	 * @return datapoint
	 */
	public Datapoint updateDatapoint(String username, long gid, Datapoint dp, String token){
		try {
			Datapoint u = service.path("goaltracking").path(username).path(""+gid).path("datapoint.update").queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Datapoint.class, dp);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("updateDatapoint(String username, long gid, Datapoint dp) process: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username
	 * @param gid
	 * @param dpId
	 * @param token
	 * @return datapoint
	 */
	public Datapoint deleteDatapoint(String username, long gid, long dpId, String token){
		try {
			Datapoint u = service.path("goaltracking").path(username).path(""+gid).path(dpId+"").path("datapoint.del").queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Datapoint.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("deleteDatapoint(String username, long gid, long dpId) process: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
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
	public String getExpectedGoalValue(long date, long gId, String token, String username){
		try {
			String u = service.path("goaltracking").path("expected").queryParam("date", date+"").queryParam("gid", gId+"").queryParam("access_token", token).queryParam("username", username).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getExpectedGoalValue(long date, long gId): process "+response.getStatus());
			 return -1+"";
		 }catch(Exception e){
			 return -2+"";
		 }	
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
	public String getFeedback(long date, long gId, String token, String username){
		try {
			String u = service.path("goaltracking").path("feedback").queryParam("date", date+"").queryParam("gid", gId+"").queryParam("username", username).queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getFeedback(long date, long gId): process "+response.getStatus());
			 return -1+"";
		 }catch(Exception e){
			 return -2+"";
		 }	
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
	public String getProgress(long date, long gId, String token, String username){
		try {
			String u = service.path("goaltracking").path("progress").queryParam("date", date+"").queryParam("username", username).queryParam("access_token", token).queryParam("gid", gId+"").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getFeedback(long date, long gId): process "+response.getStatus());
			 return -1+"";
		 }catch(Exception e){
			 return -2+"";
		 }	
	}
	/**
	 * 
	 * @return URI - for local service end
	 */
	private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://127.0.1.1:5923/").build();
	}
}
