package it.unitn.lifecoach.service.storage.out.food.client;
 
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import it.unitn.lifecoach.model.*;
import it.unitn.lifecoach.service.storage.out.food.ws.FoodInfo;
 
/**
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class FoodInfoClient{
	public static void main(String[] args) throws Exception {
		
		
		URL url = new URL("http://localhost:6902/ws/foodinfo?wsdl");
        QName qname = new QName("http://ws.food.out.storage.service.lifecoach.unitn.it/", "FoodInfoService");
        Service service = Service.create(url, qname);
        
        FoodInfo foodInfo = service.getPort(FoodInfo.class);
        foodInfo.createSession();   
        Product[] products =  foodInfo.searchProduct("pasta", 20);  
      
        System.out.println("LIST OF PRODUCTS: ");
        for (int i=0;i<products.length;i++){
        	
        	System.out.println("=================");
        	System.out.println("Upc: "+products[i].getUpc());
        	System.out.println("Name: "+products[i].getProdName());
        	System.out.println("Descr: "+products[i].getDescription());
        	System.out.println("Brand: "+products[i].getBrand());
        	System.out.println("Manuf:  "+products[i].getManufacturer());
        	System.out.println("Size: "+products[i].getProdSize());
        }
        
        Random r = new Random();
        int j =r.nextInt(products.length);
        Product product = products[j]; 
        
        System.out.println("\nUSER CHOSE PRODUCT: "+product.getProdName());
        System.out.println("INFO ABOUT THE PROFUCT SCORE: ");
        
        ProductScore ps =foodInfo.getProductScore(product.getUpc());
        System.out.println("\nUpc: "+ps.getUpc());
        System.out.println("List of nutrients: ");
        ArrayList<Nutrient> nutrients_list = ps.getNutrients();
        Nutrient[] nutrients = new Nutrient[nutrients_list.size()];
        nutrients = nutrients_list.toArray(nutrients);
        
        for (int k=0;k<nutrients.length;k++){
        	
        	System.out.println("=================");
        	System.out.println("Name: "+nutrients[k].getNutrientName());
        	System.out.println("Value:  "+nutrients[k].getNutrientValue());
        	System.out.println("UOM: "+nutrients[k].getNutrientUom());
        	System.out.println("FE Level: "+nutrients[k].getNutrientFeLevel());
        }
        
        System.out.println("\nScore: "+ps.getProductScore());
    }
}