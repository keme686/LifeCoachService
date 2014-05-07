/**
 * 
 */
package it.unitn.lifecoach.service.process.external.quotes;

import it.unitn.lifecoach.service.storage.external.quotes.adapter.QuotesAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/quotesprocess")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class QuotesProcessResource {

	/**
	 * Adapter for Motivational Quotes storage service layer
	 */
	private QuotesAdapter adapter = new QuotesAdapter();
	
	/**
	 * get randomly of "motivation","inspiration","encouragement","success","positive","happiness", "win"
	 * 
	 * @return randomly chosen quote
	 */
	@GET
	@Path("search/random")
	public String getRandomQuotes() {
		return adapter.getRandomQuotes();
	}
	
	/**
	 * search quotes by query
	 * 
	 * @param query  query text
	 * @return quote that matches the query
	 */
	@GET
	@Path("search")
	@Produces(MediaType.TEXT_PLAIN)
	public String searchQuotesText(@QueryParam("query") String query) {
		return adapter.searchQuotesText(query);
	}
	
	/**
	 * search quote by author
	 * 
	 * @param auth_name   name of the author
	 * @return quote that matches the name of the author
	 */
	@GET
	@Path("author")
	public String searchQuotesAuthor(@QueryParam("name") String auth_name) {
		return adapter.searchQuotesAuthor(auth_name);
	}
	
}
