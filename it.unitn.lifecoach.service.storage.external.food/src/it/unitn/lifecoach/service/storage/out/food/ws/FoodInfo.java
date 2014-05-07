package it.unitn.lifecoach.service.storage.out.food.ws;

import it.unitn.lifecoach.model.Product;
import it.unitn.lifecoach.model.ProductScore;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

/**
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@WebService
@SOAPBinding(style = Style.RPC ,use=Use.LITERAL)
public interface FoodInfo {
	
	/**
	 * create session to start querying to food essentials
	 */
	  @WebMethod(operationName="createSession")
	  @WebResult() 
	  public void createSession();
	  
	  /**
	   * Search food product
	   * 
	   * @param query  query string
	   * @param nrOfresults number of results to query for
	   * @return  array of food products object if successful
	   */
	  @WebMethod(operationName="searchProduct")
	  @WebResult(name="products_array")
	  public Product[] searchProduct(@WebParam(name="query") String query , @WebParam(name="nr_of_results") int nrOfresults);
	  
	  /**
	   * Get product score which food essentials give for the food
	   *  
	   * @param upc
	   * @return product score object
	   */
	  @WebMethod(operationName="getProductScore")
	  @WebResult(name="product_score")
	  public ProductScore getProductScore(@WebParam(name="upc") String upc);
}
