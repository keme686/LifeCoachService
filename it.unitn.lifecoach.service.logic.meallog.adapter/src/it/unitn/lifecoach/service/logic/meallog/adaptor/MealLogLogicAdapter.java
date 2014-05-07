package it.unitn.lifecoach.service.logic.meallog.adaptor;

import it.unitn.lifecoach.model.GraphPoint;
import it.unitn.lifecoach.model.MealLog;

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
 * Adapter for the Meal Logic Service
 *  
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class MealLogLogicAdapter {
	
	WebResource service;
	
	public MealLogLogicAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @param username
	 * @return
	 */
	public List<MealLog> getMealLogsWithinInterval(long from, long to , String username){
		
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meallogic").path("interval").queryParam("from",""+from).
					 queryParam("to", to+"").queryParam("username", username)
					 .accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @param date
	 * @param username
	 * @return
	 */
	public List<MealLog> getMealLogsofADay(long date , String username){
		
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meallogic").path("day")
					.queryParam("date",""+date).queryParam("username", username)
					 .accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @param from
	 * @param to
	 * @param username
	 * @return
	 */
	public List<GraphPoint> getCaloriesWithinInterval(long from, long to , String username){
		
		try{	 
			GenericType<List<GraphPoint>> genericListType =  new GenericType<List<GraphPoint>>() {};
			List<GraphPoint> u = service.path("meallogic").path("interval").path("cal").queryParam("from",""+from).
					 queryParam("to", to+"").queryParam("username", username)
					 .accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @param date
	 * @param username
	 * @return
	 */
	public GraphPoint getCaloriesOfADay(long date , String username){
		
		try{	 
			GraphPoint u = service.path("meallogic").path("day").path("cal")
					.queryParam("date",""+date).queryParam("username", username).get(GraphPoint.class);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 e.printStackTrace();
			 return null;
		}catch(Exception e){
			 e.printStackTrace();
			 return null;
		}
	}
	
	/**
	 * 
	 * @param log
	 * @return
	 */
	public MealLog addMealLog(MealLog log, String username){
		try{
			 MealLog u = service.path("meallogic").path(username).path("add").type(MediaType.APPLICATION_JSON).
					 accept(MediaType.APPLICATION_JSON).post(MealLog.class, log);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 e.printStackTrace();
			 return null;
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
	}
	
	/**
	 * 
	 * @param log
	 * @param mid
	 * @param username
	 * @return
	 */
	public MealLog updateMealLog(MealLog log, long mid, String username){
		try{
			 MealLog u = service.path("meallogic").path(username).path("update").path(""+mid).
					 type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(MealLog.class, log);
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
	 * @param mid
	 * @param username
	 * @return
	 */
	public String deleteMealLog(long mid, String username){
		try{
			 String u = service.path("meallogic").path(username).
					 path("delete").path(""+mid)
					 .accept(MediaType.APPLICATION_JSON).delete(String.class);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public List<MealLog> getAll(String username){
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meallogic").path(username).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @return uri - for local service end
	 */
	private static URI getBaseURI() {
		    return UriBuilder.fromUri("http://127.0.1.1:5905/").build();
	}
}
