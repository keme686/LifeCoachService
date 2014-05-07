package it.unitn.lifecoach.service.storage.out.food.client;

import it.unitn.lifecoach.model.*;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.UriBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
public class FoodEssentialsClient {

	private static FoodEssentialsClient instance = null;
	private static final String uid="elvko";
	private static final String api_key="tv9ft2hu5xcm8nepbf7cycd5";
	private static final String devid ="demoDev_lfch01";
	private static final String appid ="LifeCoach";
	private static WebResource service;
	private static String sid;
	
	public static  FoodEssentialsClient getInstance(){
	      
		  if(instance == null) {
			 	  URI uri =UriBuilder.fromUri("http://api.foodessentials.com/").build();
			 	  FoodEssentialsClient client= new FoodEssentialsClient(uri);
	    		  instance=client;
	      }
	      return instance;
	}
	
	private FoodEssentialsClient(URI u) {
		ClientConfig config = new DefaultClientConfig();		
	    Client client = Client.create(config);
		service = client.resource(u);
		createSession();
	}
	
	public String createSession(){
		
		String response =service.path("createsession").queryParam("uid", uid).
				queryParam("devid", devid).queryParam("appid", appid).
				queryParam("f", "json").queryParam("api_key", api_key).get(String.class);
		
		JSONParser jp = new JSONParser();
		String session_id="";
		try {
			JSONObject session=(JSONObject)jp.parse(response);
			session_id = (String) session.get("session_id");
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		
		sid=session_id;
		return session_id;
	}
	
	public Product[] searchProduct(String query, int nrOfresults){

		String response =service.path("searchprods").queryParam("q", query).
				queryParam("sid", sid).queryParam("n", ""+nrOfresults).queryParam("s", "1").
				queryParam("f", "json").queryParam("v", "2.00").
				queryParam("api_key", api_key).get(String.class);
		
		System.out.println(""+response+"\n");
		
		JSONParser jp = new JSONParser();
		ArrayList<Product> products_list=new  ArrayList<Product>(); 
		String upc="", name="", descr="", brand="", manufacturer="", size="";
		try {
			JSONObject prodSearchRes=(JSONObject)jp.parse(response);
			JSONArray productsArray = (JSONArray) prodSearchRes.get("productsArray");
			for (int i=0;i<productsArray.size();i++){
				JSONObject product = (JSONObject) productsArray.get(i);
				upc =  (String) product.get("upc");
				name = (String) product.get("product_name");
				descr = (String) product.get("product_description");
				brand = (String) product.get("brand");
				manufacturer = (String) product.get("manufacturer");
				size = (String) product.get("product_size");
				products_list.add(new Product(upc,name,descr,brand,manufacturer,size));
			}
		} catch (ParseException pe) {
			System.out.println(pe.getMessage());
		} 
		
		Product[] products_array = new Product[products_list.size()];
		products_array = products_list.toArray(products_array);
		
		return products_array;
	}
	
	public ProductScore getProductScore(String upc){
	
		String response =service.path("productscore").queryParam("u", upc).
				queryParam("sid", sid).queryParam("f", "json").
				queryParam("api_key", api_key).get(String.class);
		
		System.out.println(response);
		
		JSONParser jp = new JSONParser();
		ArrayList<Nutrient> nutrients = new ArrayList<Nutrient>();
		ProductScore ps= null;
		String name="", value="", uom="", fe_level="";
		long score=0;
		
		try {
			JSONObject productScoreRes =(JSONObject)jp.parse(response);
			JSONObject product = (JSONObject) productScoreRes.get("product");
			JSONArray nutrientsArray = (JSONArray) product.get("nutrients");
			if(nutrientsArray==null){
				System.out.println("NULL");
			}
			for (int i=0;i<nutrientsArray.size();i++){
				JSONObject nutrient = (JSONObject) nutrientsArray.get(i);
				name =(String) nutrient.get("nutrient_name");
				value =(String) nutrient.get("nutrient_value");
				uom =(String) nutrient.get("nutrient_uom");
				fe_level= (String) nutrient.get("nutrient_fe_level");
				nutrients.add(new Nutrient(name, value, uom, fe_level));
			}
			score = (Long) product.get("productscore");
			
			ps= new ProductScore(upc, nutrients, score);
		
		} catch (ParseException pe) {
			System.out.println(pe.getMessage());
		} 
		
		return ps;
	}
	
	public static void main(String[] args) { 		
		
		FoodEssentialsClient client = FoodEssentialsClient.getInstance();
		client.createSession();
	}

}
