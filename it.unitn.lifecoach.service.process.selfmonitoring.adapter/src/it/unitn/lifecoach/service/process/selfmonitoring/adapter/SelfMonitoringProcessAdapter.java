/**
 * 
 */
package it.unitn.lifecoach.service.process.selfmonitoring.adapter;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.model.User;

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
 * This class is used as an adapter for self monitoring process centric service. 
 * It calls all services related to {@link UserProcessResource}, {@link MeasureProcessResource} and {@link MeasureFeedbackProcessResource} 
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class SelfMonitoringProcessAdapter {
	WebResource service;
	
	public SelfMonitoringProcessAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	
	/////////////////////////////////////////////////////////////////////
	//////////////////USER //////////////////////////////////////
	////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param user a new user information to be created/stored
	 * @return	created user information
	 */
	public User addUser(User user){
		try {
			 User u = service.path("selfmonitoring").path("user").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(User.class, user);
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
	 *
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return	user information if existed, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public User getUser(String username, String token){
		try {
			 User u = service.path("selfmonitoring").path("user").path(username).queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(User.class);
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
	 * @param user	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return user information if existed, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public User updateUser(User user, String username, String token){
		try{
			User u = service.path("selfmonitoring").path("user").path(username).queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(User.class, user);
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
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return	user  information if existed, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public User deactivateUser(String username, String token){
		try{
			User u = service.path("selfmonitoring").path("user").path(username).path("deactivate").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).put(User.class);
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
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return user  information if exists, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public User activateUser(String username, String token){
		try{
			User u = service.path("selfmonitoring").path("user").path(username).path("activate").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).put(User.class);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	////////////////////////////////////////////////////////////
	///////////// MEASURE ///////////////////////////////////
	/////////////////////////////////////////////////////////
	/**
	 * adds a new measure for a user
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param measure 
	 * @return		measure  information if added, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public Measure addMease(String username, Measure measure, String token){
		try{
			Measure u = service.path("measure").path(username).queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Measure.class, measure);
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
	 * gets measure information
	 * 
	 * @param mid
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return measure information if exists, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public Measure getMeasure(long mid, String token, String username){
		try{
			Measure u = service.path("measure").path(mid+"").queryParam("access_token", token).queryParam("username", username).accept(MediaType.APPLICATION_JSON).get(Measure.class);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("self monitoring process getMeasure(long mid):"+ response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * Updates user's measure information, except name of a measure
	 * 
	 * @param measure
	 * @param mid
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return measure information if exists, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public Measure updateMeasure(Measure measure, long mid, String username, String token){
		try{
			Measure u = service.path("measure").path(username).path(mid+"").queryParam("access_token", token).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Measure.class, measure);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("self monitoring Process update:"+ response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * get list of user's measure info
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return {@link List} of {@link Measure}  information if exists, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public List<Measure> getAll(String username, String token){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measure").path(username).path("all").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * deletes measure info
	 * 
	 * @param mid
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return deletion status information, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public String deleteMeasure(long mid, String username, String token){
		try{
			String u = service.path("measure").path(username).path(""+mid).queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	 * get list of measure history as data point info
	 * 
	 * @param mid
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return {@link List} of {@link Datapoint}  information if exists, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public List<Datapoint> getHistory(long mid, String token, String username){
		try{
			GenericType<List<Datapoint>> genericListType =  new GenericType<List<Datapoint>>() {};	                
			List<Datapoint> u = service.path("measure").path(""+mid).path("history").queryParam("access_token", token).queryParam("username", username).accept(MediaType.APPLICATION_JSON).get(genericListType);
			Collections.sort(u, new Comparator<Datapoint>() {
				@Override
			    public int compare(Datapoint c1, Datapoint c2) {
			        return (int)(c1.getTimestamp() - c2.getTimestamp()); 
			    }
			});
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
	 * updates measure history
	 * 
	 * @param dpId
	 * @param dpoint
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return	{@link Datapoint}  information if updated, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public Datapoint updateHistory(long dpId, Datapoint dpoint, String token, String username){
		try{
			Datapoint u = service.path("measure").path("history").path(dpId+"").path("update").queryParam("access_token", token).queryParam("username", username).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Datapoint.class, dpoint);
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
	 * Delete measure history
	 * 
	 * @param dpId
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return deletion  information if success, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public String deleteHistory(long dpId, String token, String username){
		try{
			String u = service.path("measure").path("history").path(""+dpId).path("del").queryParam("access_token", token).queryParam("username", username).accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	 * get suggested measures for lifestyle and health self-monitoring and goal tracking
	 * 
	 * @return
	 */
	public List<String> getSuggestedMeasures(){
		try{
			GenericType<List<String>> genericListType =  new GenericType<List<String>>() {};	                
			List<String> u = service.path("measure").path("suggested").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			e.printStackTrace();
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	////////////////////////////////////////////////////////////////////////////
	////////////////MEASURE FEEDBACK ////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return bmi  information if exists, Response 401 if not authenticated,  Response 204 otherwise
	 */
	public String getBmi(String username, String token){
		try{
			String u = service.path("measurefeedback").path(username).path("bmi").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("getBmi process response : "+  response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return list of measure
	 */
	public List<Measure> getDailyDataFeedReminder(String username, String token){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurefeedback").path(username).path("dfreminder").path("daily").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param mid
	 * @return list of measure
	 */
	public String getDataFeedReminderForMeasure(String username, long mid, String token){
		try{
			String u = service.path("measurefeedback").path(username).path("dfreminder").path(mid+"").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(String.class);
			 return u;
		}catch(UniformInterfaceException e){
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/**
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return list of measure
	 */
	public List<Measure> getB4WeekDataFeedReminder(String username, String token){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurefeedback").path(username).path("dfreminder").path("b4week").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return list of measure
	 */
	public List<Measure> getWeeklyDataFeedReminder(String username, String token){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurefeedback").path(username).path("dfreminder").path("week").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return list of measure
	 */
	public List<Measure> getAllMissingDataFeedReminder(String username, String token){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurefeedback").path(username).path("dfreminder").path("allmissing").queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 return null;
		 }catch(Exception e){
			 return null;
		 }		
	}
	/**
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param mid
	 * @param days
	 * @return sum of values
	 */
	public double getTotalValue(String username, long mid, int days, String token){
		try{
			double u = service.path("measurefeedback").path(username).path("total").path(mid+"").path(""+days).queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(Double.class);
			 return u;
		}catch(UniformInterfaceException e){
			 return -2.0;
		 }catch(Exception e){
			 return -2.0;
		 }
	}
	/**
	 * 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @param mid
	 * @param days
	 * @return average
	 */
	public double getAverageValue(String username, long mid, int days, String token){
		try{
			double u = service.path("measurefeedback").path(username).path("average").path(mid+"").path(""+days).queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(Double.class);
			 return u;
		}catch(UniformInterfaceException e){
			 return -2.0;
		 }catch(Exception e){
			 return -2.0;
		 }
	}
	/**
	 * 
	 * @return uri - for local service end
	 */
	private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://127.0.1.1:5921/").build();
	}
}
