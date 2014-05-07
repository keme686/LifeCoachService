package isde.adapter.quotes.client;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class Stands4Client {

	private static Stands4Client instance = null;
	private static final String uid="3182";
	private static final String token="lVgIJleQCQTBCKVX";
	private static WebResource service;
	
	public static  Stands4Client getInstance(){
	      
		  if(instance == null) {
			 	  URI uri =UriBuilder.fromUri("http://www.stands4.com/services/v2/quotes.php?").build();
			 	  Stands4Client client= new Stands4Client(uri);
	    		  instance=client;
	      }
	      return instance;
	}
	
	private Stands4Client(URI u) {
		ClientConfig config = new DefaultClientConfig();		
	    Client client = Client.create(config);
		service = client.resource(u);
	}
	
	public String getQuotes(String search_type, String query){
		String response =service.queryParam("uid", uid).queryParam("tokenid", token).
				queryParam("searchtype", search_type).queryParam("query", query).
				accept("application/xml").get(String.class);
		return response;
	}
	
	public static void main(String[] args) { 		
		//http://www.stands4.com/services/v2/quotes.php?uid=3182&tokenid=lVgIJleQCQTBCKVX&searchtype=AUTHOR&query=Albert+Einstein
		Stands4Client client = Stands4Client.getInstance();
		System.out.print(client.getQuotes("SEARCH", "food"));
	}

}
