package it.unitn.lifecoach.service.storage.external.quotes;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;

import isde.adapter.quotes.client.Stands4Client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This resource is used to query quotes from external site and parse it based on life coach need
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/quotes")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML,MediaType.TEXT_PLAIN})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.TEXT_PLAIN})
public class QuotesResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	/**
	 * get randomly of "motivation","inspiration","encouragement","success","positive","happiness", "win"
	 * 
	 * @return randomly chosen quote
	 */
	@GET
	@Path("search/random")
	public String getRandomQuotes() {
		
		String queries[]= {"motivation","inspiration","encouragement","success","positive","happiness", "win"};
		
		Random random = new Random();
		int i=random.nextInt(6);
		String query = queries[i];
		Stands4Client stands4 = Stands4Client.getInstance();
		String quotes=stands4.getQuotes("SEARCH", query);
		System.out.println(quotes);
		String quote = parseReponse(quotes);
		return quote;
	}
	/**
	 * search quotes by query
	 * 
	 * @param query  query text
	 * @return quote that matches the query
	 */
	@GET
	@Path("search")
	public String searchQuotesText(@QueryParam("query") String query) {
		Stands4Client stands4 = Stands4Client.getInstance();
		String quotes=stands4.getQuotes("SEARCH", query);
		System.out.println(quotes);
		String quote = parseReponse(quotes);
		return quote;
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
		Stands4Client stands4 = Stands4Client.getInstance();
		String quotes=stands4.getQuotes("AUTHOR", auth_name);
		System.out.println(quotes);
		String quote = parseReponse(quotes);
		return quote;
	}
	/**
	 * 
	 * @param response
	 * @return string
	 */
	private String parseReponse(String response){
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		
		ArrayList<String> quotes = new ArrayList<String>();
		
		try {
		    db = dbf.newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(response));
		    
		    try {    	
		        Document doc = db.parse(is);
		        XPathExpression expr = xpath.compile("/results/result");
		    	NodeList results = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		    	NodeList result = null;
		    	String quote="";
		    	String author="";
		    	for(int i=0;i<results.getLength();i++){
		    		result =  results.item(i).getChildNodes();
		    		
		    		for(int j=0;j<result.getLength();j++){
		    			
		    			if(result.item(j).getNodeName().compareTo("quote")==0){
		    				quote="\""+result.item(j).getTextContent()+"\"";
		    			}
		    			
		    			if(result.item(j).getNodeName().compareTo("author")==0){
		    				author=result.item(j).getTextContent();
		    			}
		    		}
		    		quotes.add(quote+"\n- "+author);
		    	}
		    	
		    } catch (SAXException saxEx) {
		        System.out.println(saxEx.getMessage());
		        quotes = null;
		    } catch (IOException ioEx) {
		    	System.out.println(ioEx.getMessage());
		    	quotes = null;
		    } catch (XPathExpressionException xpEx) {
		    	System.out.println(xpEx.getMessage());
		    	quotes = null;
			}
		} catch (ParserConfigurationException prsConfEx) {
			System.out.println(prsConfEx.getMessage());
	    	quotes = null;
		}
		
		if(quotes==null){
			return "Something happened! We were not able to return a quote :("
					+ "\nWe will try to fix it soon"
					+ " and return with more great quotes."
					+ "\nFor now, this is my favorite one: "
					+ "\n\"If you want to live a happy life, tie it to a goal,"
					+ "\n not to people or things\"\n Albert Einstein"  ;
		} 
		
		Random r=  new Random();
		int i= r.nextInt(quotes.size());
		
		return quotes.get(i);
	}
}
