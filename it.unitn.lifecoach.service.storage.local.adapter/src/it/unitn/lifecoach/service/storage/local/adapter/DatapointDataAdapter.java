/**
 * 
 */
package it.unitn.lifecoach.service.storage.local.adapter;

import it.unitn.lifecoach.model.Datapoint;

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
public class DatapointDataAdapter {

	WebResource service;
	
	public DatapointDataAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Datapoint getDatapoint(long id){
		try{
			Datapoint u = service.path("datapointdata").path(id+"").accept(MediaType.APPLICATION_JSON).get(Datapoint.class);
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
	 * @param dpoint
	 * @return
	 */
	public Datapoint addDatapoint(Datapoint dpoint){
		try{
			Datapoint u = service.path("datapointdata").path("add").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Datapoint.class, dpoint);
			 return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println("addDatapoint(Datapoint dpoint) storage: "+response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	/***
	 * 
	 * @param dpoint
	 * @param id
	 * @return
	 */
	public Datapoint updateDatapoint(Datapoint dpoint, long id){
		try{
			Datapoint u = service.path("datapointdata").path(id+"").path("update").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Datapoint.class, dpoint);
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
	 * @param id
	 * @return
	 */
	public String deleteDatapoint(long id){
		try{
			String u = service.path("datapointdata").path(""+id).path("del").accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	 * @return
	 */
	public List<Datapoint> getAll(){
		try{
			GenericType<List<Datapoint>> genericListType =  new GenericType<List<Datapoint>>() {};	                
			List<Datapoint> u = service.path("datapointdata").path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @return
	 */
	public List<Datapoint> getAll(long mid){
		try{
			GenericType<List<Datapoint>> genericListType =  new GenericType<List<Datapoint>>() {};	                
			List<Datapoint> u = service.path("datapointdata").path(""+mid).path("measure").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public List<Datapoint> getGoalDatapoints(long gId){
		try{
			GenericType<List<Datapoint>> genericListType =  new GenericType<List<Datapoint>>() {};	                
			List<Datapoint> u = service.path("datapointdata").path(""+gId).path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
			return u;
		}catch(UniformInterfaceException e){
			 ClientResponse response = e.getResponse();
			System.out.println(response.getStatus());
			 return null;
		 }catch(Exception e){
			 return null;
		 }
	}
	
	public Datapoint getLastDatapoint(long gId){
		List<Datapoint> dps = this.getGoalDatapoints(gId);
	    if(dps !=null && dps.size()>0)
	    	return dps.get(dps.size()-1);
	    return null;
	}
	/**
	 * 
	 * @return uri - for local service end
	 */
	private static URI getBaseURI() {
	    return UriBuilder.fromUri("http://127.0.1.1:5900/").build();
	}
}
