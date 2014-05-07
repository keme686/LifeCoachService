/**
 * 
 */
package it.unitn.lifecoach.service.logic.selfmonitoring.adapter;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Measure;

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
public class MeasureLogicAdapter {
	
	WebResource service;
	
	public MeasureLogicAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	
	/**
	 * adds a new measure for a user
	 * 
	 * @param username
	 * @param measure
	 * @return
	 */
	public Measure addMease(String username, Measure measure){
		try{
			Measure u = service.path("measurelogic").path(username).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Measure.class, measure);
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
	 * @return
	 */
	public Measure getMeasure(long mid){
		try{
			Measure u = service.path("measurelogic").path(mid+"").accept(MediaType.APPLICATION_JSON).get(Measure.class);
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
	 * updates user's measure information, except name of a measure
	 * 
	 * @param measure
	 * @param mid
	 * @param username
	 * @return
	 */
	public Measure updateMeasure(Measure measure, long mid, String username){
		try{
			Measure u = service.path("measurelogic").path(username).path(mid+"").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Measure.class, measure);
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
	 * get list of user's measure info
	 * 
	 * @param username
	 * @return
	 */
	public List<Measure> getAll(String username){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measurelogic").path(username).path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @param username
	 * @return
	 */
	public String deleteMeasure(long mid, String username){
		try{
			String u = service.path("measurelogic").path(username).path(""+mid).accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	 * @return
	 */
	public List<Datapoint> getHistory(long mid){
		try{
			GenericType<List<Datapoint>> genericListType =  new GenericType<List<Datapoint>>() {};	                
			List<Datapoint> u = service.path("measurelogic").path(""+mid).path("history").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @return
	 */
	public Datapoint updateHistory(long dpId, Datapoint dpoint){
		try{
			Datapoint u = service.path("measurelogic").path("history").path(dpId+"").path("update").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Datapoint.class, dpoint);
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
	 * delete measure history
	 * 
	 * @param dpId
	 * @return
	 */
	public String deleteHistory(long dpId){
		try{
			String u = service.path("measurelogic").path("history").path(""+dpId).path("del").accept(MediaType.APPLICATION_JSON).delete(String.class);
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
			List<String> u = service.path("measurelogic").path("suggested").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
		    return UriBuilder.fromUri("http://127.0.1.1:5909/").build();
	}
}
