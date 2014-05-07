package it.unitn.lifecoach.service.process.meallog;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import it.unitn.lifecoach.model.*;
import it.unitn.lifecoach.service.storage.out.food.ws.FoodInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * This class is an adapter to call the endpoint published in the FoodInfo Service
 * FoodInfo service was implemented as soap service. 
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class FoodInfoResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	/**
	 * FoodInfo class is the interface of the FoodInfo service implementation
	 * We are using this interface to create a proxy of the FoodInfo service,
	 * and access the different methods it offers. 
	 * 
	 */
	private static final FoodInfo foodInfo;

	static {
		URL url = null;
		try {
			url = new URL("http://localhost:6902/ws/foodinfo?wsdl");
		
		} catch (MalformedURLException e) {
			System.out.println("Error service url not correct");
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		QName qname = new QName("http://ws.food.out.storage.service.lifecoach.unitn.it/", "FoodInfoService");
        Service service = Service.create(url, qname);
		foodInfo = service.getPort(FoodInfo.class);
		foodInfo.createSession();
      
	}
	
	public FoodInfoResource(UriInfo uriInfo, Request request){
		
		this.uriInfo =uriInfo;
		this.request =request;
	}
	
	/**
	 * FoodInfo service will create new session It will save the session id and
	 * use it for all following request done to this service
	 */
	 @GET
	 @Path("session")
	 @Produces(MediaType.TEXT_PLAIN)
	 public String createNewSession() {
		foodInfo.createSession();
		return "New Session Created!";
	 }
	
	/**
	 * Search for product using string query. Specify maximum number of result
	 * to be returned Get back the results of the search as an array of products
	 * 
	 * @param query
	 * @param numberOfResults
	 * @return
	 */
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> searchProduct(@QueryParam("query") String query,@QueryParam("n") int numberOfResults) {
	
		Product[] products =  foodInfo.searchProduct(query,	numberOfResults);
		List<Product> response = Arrays.asList(products);
		
		return response;
	}
	
	/**
	 * Get the score and nutrients about a specific product
	 * 
	 * @param upc
	 * @return
	 */
	@GET
	@Path("score")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductScore getProductScore(@QueryParam("upc") String upc) {
	
		ProductScore ps = foodInfo.getProductScore(upc);
		return ps;
	}	
}
