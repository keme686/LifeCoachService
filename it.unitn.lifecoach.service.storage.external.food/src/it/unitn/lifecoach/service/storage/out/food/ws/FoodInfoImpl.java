package it.unitn.lifecoach.service.storage.out.food.ws;

import it.unitn.lifecoach.model.Product;
import it.unitn.lifecoach.model.ProductScore;
import it.unitn.lifecoach.service.storage.out.food.client.FoodEssentialsClient;

import javax.jws.WebService;

/**
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@WebService(endpointInterface = "it.unitn.lifecoach.service.storage.out.food.ws.FoodInfo",
serviceName="FoodInfoService")

public class FoodInfoImpl implements FoodInfo {

	/**
	 * Create session to query to food essentials
	 */
	@Override
	public void createSession() {
		FoodEssentialsClient client = FoodEssentialsClient.getInstance();
		client.createSession();
	}

	/**
	 * search product
	 */
	@Override
	public Product[] searchProduct(String query, int nrOfresults) {
		FoodEssentialsClient client = FoodEssentialsClient.getInstance();
		Product[] products = client.searchProduct(query, nrOfresults);
		return products;
	}

	/**
	 * get product score
	 */
	@Override
	public ProductScore getProductScore(String upc) {
		FoodEssentialsClient client = FoodEssentialsClient.getInstance();
		ProductScore ps = client.getProductScore(upc);
		return ps;
	}

}
