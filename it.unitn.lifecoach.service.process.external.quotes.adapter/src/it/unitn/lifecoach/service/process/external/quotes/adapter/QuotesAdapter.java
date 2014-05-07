/**
 * 
 */
package it.unitn.lifecoach.service.process.external.quotes.adapter;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class QuotesAdapter {
	
	/**
	 * 
	 */
	WebResource service;
	/**
	 * 
	 */
	public QuotesAdapter() {
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    service = client.resource(getBaseURI());
	}
	
	/**
	 * get randomly of "motivation","inspiration","encouragement","success","positive","happiness", "win"
	 * 
	 * @return
	 */
	public String getRandomQuotes() {
		try {
			 String u = service.path("quotesprocess").path("search").path("random").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
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
	 * Search by text
	 * 
	 * @param query
	 * @return string of quotes text
	 */
	public String searchQuotesText(String query) {
		try {
			 String u = service.path("quotesprocess").path("search").queryParam("query", query).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
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
	 * search by author 
	 * 
	 * @param auth_name
	 * @return string of quote
	 */
	public String searchQuotesAuthor(String auth_name) {
		try {
			 String u = service.path("quotesprocess").path("author").queryParam("name", auth_name).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(String.class);
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
		    return UriBuilder.fromUri("http://127.0.1.1:5930/").build();
	}
}
