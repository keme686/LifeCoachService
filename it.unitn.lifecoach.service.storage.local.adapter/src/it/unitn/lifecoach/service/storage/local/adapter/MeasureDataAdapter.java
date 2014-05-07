/**
 * 
 */
package it.unitn.lifecoach.service.storage.local.adapter;

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
public class MeasureDataAdapter {

	WebResource service;
	public MeasureDataAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Measure getMeasure(long id){
		try{
			Measure u = service.path("measuredata").path(id+"").accept(MediaType.APPLICATION_JSON).get(Measure.class);
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
	 * @param measure
	 * @return
	 */
	public Measure addMeasure(Measure measure){
		try{
			Measure u = service.path("measuredata").path("add").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Measure.class, measure);
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
	 * @param measure
	 * @param mid
	 * @return
	 */
	public Measure updateMeasure(Measure measure, long mid){
		try{
			Measure u = service.path("measuredata").path(mid+"").path("update").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(Measure.class, measure);
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
	public String deleteMeasure(long mid){
		try{
			String u = service.path("measuredata").path(""+mid).path("del").accept(MediaType.APPLICATION_JSON).delete(String.class);
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
	public List<Measure> getAll(){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measuredata").path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	 * @return
	 */
	public List<Measure> getAll(String username){
		try{
			GenericType<List<Measure>> genericListType =  new GenericType<List<Measure>>() {};	                
			List<Measure> u = service.path("measuredata").path(username).path("all").accept(MediaType.APPLICATION_JSON).get(genericListType);
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
	    return UriBuilder.fromUri("http://127.0.1.1:5900/").build();
	}
}
