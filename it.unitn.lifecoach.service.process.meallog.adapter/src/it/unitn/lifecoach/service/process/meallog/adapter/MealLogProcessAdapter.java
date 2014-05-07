package it.unitn.lifecoach.service.process.meallog.adapter;

import it.unitn.lifecoach.model.GraphPoint;
import it.unitn.lifecoach.model.MealLog;
import it.unitn.lifecoach.model.Product;
import it.unitn.lifecoach.model.ProductScore;

import java.net.URI;
import java.util.ArrayList;
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
public class MealLogProcessAdapter {
	
	WebResource service;
	
	public MealLogProcessAdapter() {
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
	public List<MealLog> getMealLogsWithinInterval(long from, long to , String username, String token){
		
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meal").path("process").path("interval").queryParam("from",""+from).
					 queryParam("to", to+"").queryParam("username", username).queryParam("access_token", token)
					 .accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getMealLogsWithinInterval(long from, long to , String username, String token) process: "+response.getStatus());
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
	public List<MealLog> getMealLogsofADay(long date , String username, String token){
		
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meal").path("process").path("day")
					.queryParam("date",""+date).queryParam("username", username).queryParam("access_token", token)
					 .accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getMealLogsofADay(long date , String username) process: "+response.getStatus());
			 return null;
		}catch(Exception e){
			 return null;
		}
	}
	
	/**
	 * list meal logs for a day for a specific serving time, such as Breakfast, dinner, morning snack,...
	 * 
	 * @param date
	 * @param servingTime
	 * @param username
	 * @return
	 */
	public List<MealLog> getMealLogsofDayPerSevingTime(long date , String servingTime, String username, String token){
		
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meal").path("process").path("day")
					.queryParam("date",""+date).queryParam("username", username).queryParam("access_token", token)
					 .accept(MediaType.APPLICATION_JSON).get(genericListType);
			List<MealLog> us = new ArrayList<>();
			if(u!=null && servingTime != null){
				for(MealLog m: u){
					if(m.getServingTime().equals(servingTime)){
						us.add(m);
					}
				}
			}
				
			return us;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getMealLogsofADay(long date , String username) process: "+response.getStatus());
			 return null;
		}catch(Exception e){
			e.printStackTrace();
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
	public List<GraphPoint> getCaloriesWithinInterval(long from, long to , String username, String token){
		
		try{	 
			GenericType<List<GraphPoint>> genericListType =  new GenericType<List<GraphPoint>>() {};
			List<GraphPoint> u = service.path("meal").path("process").path("interval").path("cal").queryParam("access_token", token).queryParam("from",""+from).
					 queryParam("to", to+"").queryParam("username", username)
					 .accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getCaloriesWithinInterval(long from, long to , String username) process: "+response.getStatus());
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
	public GraphPoint getCaloriesOfADay(long date , String username, String token){
		
		try{	 
			GraphPoint u = service.path("meal").path("process").path("day").path("cal")
					.queryParam("date",""+date).queryParam("username", username).queryParam("access_token", token).get(GraphPoint.class);
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
	public MealLog addMealLog(MealLog log, String username, String token){
		try{
			 MealLog u = service.path("meal").path("process").path("add").
					 queryParam("username", username).queryParam("access_token", token).type(MediaType.APPLICATION_JSON).
					 accept(MediaType.APPLICATION_JSON).post(MealLog.class, log);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("addMealLog(MealLog log, String username) process: "+response.getStatus());
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
	public MealLog updateMealLog(MealLog log, long mid, String username, String token){
		try{
			 MealLog u = service.path("meal").path("process").path("update").
					 queryParam("mid", ""+mid).queryParam("access_token", token).queryParam("username", username).
					 type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(MealLog.class, log);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("updateMealLog(MealLog log, long mid, String username) process: "+response.getStatus());
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
	public String deleteMealLog(long mid, String username, String token){
		try{
			 String u = service.path("meal").path("process").path("del").queryParam("mid", ""+mid)
					 .queryParam("username", username).queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	 * @param query
	 * @param n
	 * @return
	 */
	public List<Product> getSearchFood(String query, int n){
		try{
			GenericType<List<Product>> genericListType =  new GenericType<List<Product>>() {};
			 List<Product> u = service.path("meal").path("process").path("info").path("search")
					 .queryParam("query", ""+query).queryParam("n", ""+n).
					 accept(MediaType.APPLICATION_JSON).get(genericListType);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			 System.out.println("getSearchFood(String query, int n) process: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	/**
	 * 
	 * @param upc
	 * @return
	 */
	public ProductScore getFoodScore(String upc){
		try{			
			ProductScore u = service.path("meal").path("process").path("info").path("score")
					 .queryParam("upc", upc).accept(MediaType.APPLICATION_JSON).get(ProductScore.class);
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
	 * @return
	 */
	public List<MealLog> getAll(String username, String token){
		try{
			GenericType<List<MealLog>> genericListType =  new GenericType<List<MealLog>>() {};	                
			List<MealLog> u = service.path("meal").path("process").path(username).queryParam("access_token", token).accept(MediaType.APPLICATION_JSON).get(genericListType);
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
		    return UriBuilder.fromUri("http://127.0.1.1:5910/").build();
	}
}
