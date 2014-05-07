package it.unitn.lifecoach.service.process.meallog;

import it.unitn.lifecoach.model.GraphPoint;
import it.unitn.lifecoach.model.MealLog;
import it.unitn.lifecoach.service.logic.meallog.adaptor.MealLogLogicAdapter;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/meal/process")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class FoodResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	private MealLogLogicAdapter adapter = new MealLogLogicAdapter();
	private UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();
	
	@Path("info")
	public FoodInfoResource getInfo() {
		return new FoodInfoResource(uriInfo, request);
	}
	
	/**
	 * 
	 * @param log
	 * @param username
	 * @return
	 */
	@POST
	@Path("add")
	public Response addMealLog(MealLog log, @QueryParam("username") String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return Response.noContent().build();
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		
		return Response.ok(adapter.addMealLog(log, username)).build();
	}
	
	/**
	 * 
	 * @param log
	 * @param mid
	 * @param username
	 * @return
	 */
	@PUT
	@Path("update")
	public Response updateMealLog(MealLog log, @QueryParam("mid") long mid , @QueryParam("username") String username, @QueryParam("access_token")String token){
		if(username == null || token == null || log == null || mid == 0)
			return Response.noContent().build();
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		
		return Response.ok(adapter.updateMealLog(log, mid, username)).build();
	}
	
	/**
	 * 
	 * @param mid
	 * @param username
	 * @return
	 */
	@DELETE
	@Path("del")
	public Response deleteMealLog( @QueryParam("mid") long mid , @QueryParam("username") String username, @QueryParam("access_token")String token){
		if(username == null || token == null || mid == 0)
			return Response.noContent().build();
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		
		return Response.ok(adapter.deleteMealLog(mid, username)).build();
	}
	
	/**
	 * 
	 * @param date
	 * @param username
	 * @return
	 */
	@GET
	@Path("day")
	public List<MealLog> getWithinDay(@QueryParam("date") long date, @QueryParam("username") String username, @QueryParam("access_token")String token) {
		if(username == null || token == null || date == 0)
			return null;
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}

		return adapter.getMealLogsofADay(date, username);	
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param username
	 * @return
	 */
	@GET
	@Path("interval")
	public List<MealLog> getWithinInterval(@QueryParam("from") long from,
			@QueryParam("to") long to, @QueryParam("username") String username, @QueryParam("access_token")String token) {
		if(username == null || token == null || from == 0 || to == 0)
			return null;
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}

		return adapter.getMealLogsWithinInterval(from, to, username);	
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param username
	 * @return
	 */
	@GET
	@Path("interval/cal")
	public List<GraphPoint> getCaloriesWithinInterval(@QueryParam("from") long from,
			@QueryParam("to") long to, @QueryParam("username") String username, @QueryParam("access_token")String token) {
		if(username == null || token == null || from == 0 || to == 0)
			return null;
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		
		return adapter.getCaloriesWithinInterval(from, to, username);	
	}
	
	/**
	 * 
	 * @param date
	 * @param username
	 * @return
	 */
	@GET
	@Path("day/cal")
	public GraphPoint getCaloriesWithinInterval(@QueryParam("date") long date
			,@QueryParam("username") String username, @QueryParam("access_token")String token) {
		if(username == null || token == null || date == 0 )
			return null;
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		
		return adapter.getCaloriesOfADay(date, username);	
	}
	/**
	 * 
	 * @param username
	 * @param token
	 * @return
	 */
	@GET
	@Path("{username}")
 	public List<MealLog> getMealLogs(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null )
			return null;
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		
		return adapter.getAll(username);
 	}
}
