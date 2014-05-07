/**
 * 
 */
package it.unitn.lifecoach.service.logic.goaltracking.adapter;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Goal;

import java.net.URI;
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
public class GoalLogicAdapter {
	WebResource service;
	
	public GoalLogicAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	
	public Goal addGoal(Goal goal, String username){
		try {
			Goal u = service.path("goallogic").path(username).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Goal.class, goal);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("addGoal(Goal goal, String username) logic: "+ response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public Goal getGoal(String username, long gId){
		try {
			Goal u = service.path("goallogic").path(username).path(gId+"").accept(MediaType.APPLICATION_JSON).get(Goal.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getGoal(String username, long gId) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public Goal updateGoal(Goal goal, String username, long gId){
		try{
			Goal u = service.path("goallogic").path(username).path(""+gId).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Goal.class, goal);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("updateGoal(Goal goal, String username, long gId) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public Goal cancelGoal(String username, long gId){
		try{
			Goal u = service.path("goallogic").path(username).path(""+gId).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete(Goal.class);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("cancelGoal(String username, long gId) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public List<Goal> getActiveGoals(String username){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goallogic").path(username).path("active").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("logic getActiveGoals(String username): "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 System.out.println("logic getActiveGoals(String username) general exceptio " + e.getMessage());
			 return null;
		 }
	}
	
	public Goal getActiveGoal(String username, long mid){
		try{
			Goal u = service.path("goallogic").path(username).path(""+mid).path("active").accept(MediaType.APPLICATION_JSON).get(Goal.class);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public List<Goal> getCanceledGoals(String username){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goallogic").path(username).path("canceled").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getCanceledGoals(String username) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public List<Goal> getAll(String username){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goallogic").path(username).path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getAll(String username) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public List<Goal> getAchievedGoals(String username){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goallogic").path(username).path("achieved").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getAchievedGoals(String username) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public List<Goal> getLostGoals(String username){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goallogic").path(username).path("lost").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getLostGoals(String username) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	public List<Goal> getAll(String username, long mid){
		try{
			GenericType<List<Goal>> genericListType =  new GenericType<List<Goal>>() {};	                
			List<Goal> u = service.path("goallogic").path(username).path("measure").path(""+mid).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getAll(String username, long mid) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public List<Datapoint> getDatapoints(long gId){
		try{
			GenericType<List<Datapoint>> genericListType =  new GenericType<List<Datapoint>>() {};	                
			List<Datapoint> u = service.path("goallogic").path(gId+"").path("datapoints").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(" getDatapoints(long gId) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public Datapoint addDatapoint(String username, long gid, Datapoint dp){
		try {
			Datapoint u = service.path("goallogic").path(username).path(""+gid).path("datapoint.add").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Datapoint.class, dp);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("addDatapoint(String username, long gid, Datapoint dp) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public Datapoint updateDatapoint(String username, long gid, Datapoint dp){
		try {
			Datapoint u = service.path("goallogic").path(username).path(""+gid).path("datapoint.update").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Datapoint.class, dp);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(" updateDatapoint(String username, long gid, Datapoint dp) logic: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public Datapoint deleteDatapoint(String username, long gid, long dpId){
		try {
			Datapoint u = service.path("goallogic").path(username).path(""+gid).path(dpId+"").path("datapoint.del").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete(Datapoint.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("deleteDatapoint(String username, long gid, long dpId): "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	
	//////////////////////////////////////////////////////////////
	//////////GOAL FEEDBACKS ///////////////////////////////////
	//////////////////////////////////////////////////////////
	public String getExpectedGoalValue(long date, long gId){
		try {
			String u = service.path("goalfeedbacklogic").path("expected").queryParam("date", date+"").queryParam("gid", gId+"").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getExpectedGoalValue(long date, long gId): logic "+response.getStatus());
			 return -1+"";
		 }catch(Exception e){
			 return -2+"";
		 }	
	}
	
	public String getFeedback(long date, long gId){
		try {
			String u = service.path("goalfeedbacklogic").path("feedback").queryParam("date", date+"").queryParam("gid", gId+"").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getFeedback(long date, long gId): logic "+response.getStatus());
			 return -1+"";
		 }catch(Exception e){
			 return -2+"";
		 }	
	}
	public String getProgress(long date, long gId){
		try {
			String u = service.path("goalfeedbacklogic").path("progress").queryParam("date", date+"").queryParam("gid", gId+"").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		 }catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getFeedback(long date, long gId): logic "+response.getStatus());
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
		    return UriBuilder.fromUri("http://127.0.1.1:5925/").build();
	}
	
}
