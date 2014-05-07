/**
 * 
 */
package it.unitn.lifecoach.service.process.selfmonitoring;

import java.util.List;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.service.logic.selfmonitoring.adapter.MeasureLogicAdapter;
import it.unitn.lifecoach.service.process.authentication.adapter.UserAuthenticationAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class is used to manage user's {@link Measure} and {@link Datapoint} information for self monitoring 
 * 
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/measure")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class MeasureProcessResource {
	
	/**
	 * Adapter for self monitoring measure service
	 */
	private MeasureLogicAdapter adapter = new MeasureLogicAdapter();
	/**
	 * Adapter for authentication service 
	 */
	private UserAuthenticationAdapter authAdapter = new UserAuthenticationAdapter();
		
	/** Create a new Data Measure for a user
	 * 
	 * @param username id of a user adding a measure	
	 * @param measure	a new {@link Measure} object to be added to the system
	 * @param token		authentication token to generated while user logs in
	 * @return		Response 204 if parameter value is null or if measure with specified mid doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link Measure} detail information<br /> 
	 */
	@POST
	@Path("{username}")
	public Response addMeasure(@PathParam("username")String username, Measure measure, @QueryParam("access_token")String token){		
		if(username == null || measure == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		Measure m = adapter.addMease(username, measure);
		if(m == null)
			return Response.notModified().build();
		return Response.ok(m).build();
	}
	
	/**
	 * Update measure data, except name of a measure.
	 * 
	 * @param measure a {@link Measure} object to be updated
	 * @param mid  	id of a measure to be updated
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if measure with specified mid doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link Measure} detail information<br /> 
	 */
	@PUT
	@Path("{username}/{mid}")
	public Response updateMeasure(Measure measure, @PathParam("mid") long mid, @PathParam("username")String username, @QueryParam("access_token")String token){		
		if(measure == null || mid == 0 || username == null || token == null)
			return Response.noContent().build();
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		Measure m = adapter.updateMeasure(measure, mid, username);
		if(m == null)
			return Response.notModified().build();
		return Response.ok(m).build();
	}
	
	/**
	 * Get measure information
	 * 
	 * @param mid	an id of a measure to get its detail
	 * @param username  id of a user
	 * @param token   authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if measure with specified mid doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link Measure} detail information<br /> 
	 */
	@GET
	@Path("{mid}")
	public Response getMeasure(@PathParam("mid")long mid, @QueryParam("username")String username, @QueryParam("access_token")String token){
		if(mid == 0 || username == null || token == null)
			return Response.noContent().build();
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		
		Measure m = adapter.getMeasure(mid);
		if(m == null)
			return Response.noContent().build();
		return Response.ok(m).build();
	}
	
	/**
	 * delete measure information including datapoints associated with it.
	 * this operation can be done if this measure is not associated to a goal
	 * 
	 * @param mid	id of a measure to be deleted
	 * @param username  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if measure with specified mid doesnt exist or if deletion not success<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with status = true for deletion<br /> 
	 */
	@DELETE
	@Path("{username}/{mid}")
	public Response deleteMeasure(@PathParam("mid")long mid, @PathParam("username")String username, @QueryParam("access_token")String token){
		if(mid == 0 || username == null || token == null)
			return Response.noContent().build();
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		return Response.ok(adapter.deleteMeasure(mid, username)).build();
	}
	
	/**
	 * Gets all measures associated with this username
	 *  
	 * @param username id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if measure with specified mid doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link List} of {@link Measure} detail information<br /> 
	 */
	@GET
	@Path("{username}/all")
	public List<Measure> getAll(@PathParam("username")String username, @QueryParam("access_token")String token){
		if(username == null || token == null)
			return null;
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getAll(username);
	}
	
	/**
	 *  Gets list of datapoints as a History for this measure mId
	 *   
	 * @param mId 		id of a measure 
	 * @param username	  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if measure with specified mid doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link List} of {@link Datapoint} detail information<br /> 
	 */
	@GET
	@Path("{mid}/history")
	public List<Datapoint> getHistory(@PathParam("mid")long mId, @QueryParam("username")String username, @QueryParam("access_token")String token){
		if(mId == 0 || username == null || token == null)
			return null;
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return null;
		}
		return adapter.getHistory(mId);
	}
	
	/**
	 * update measure datapoint (history) identified by dpId
	 * 
	 * @param dpId 		id of {@link Datapoint}
	 * @param dpoint	{@link Datapoint} object to be updated
	 * @param username  id of a user
	 * @param token  	authentication token generated while the user logs in
	 * @return 		Response 204 if parameter value is null or if measure with specified mid doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link Datapont} detail information<br /> 
	 */
	@PUT
	@Path("history/{dpId}/update")
	public Response updateHistory(@PathParam("dpId")long dpId, Datapoint dpoint,@QueryParam("username")String username, @QueryParam("access_token")String token){
		if(dpId ==0 || dpoint == null || username == null || token == null)
			return Response.noContent().build();
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		
		return Response.ok(adapter.updateHistory(dpId, dpoint)).build();
	}
	
	/**
	 *  Delete measure history
	 *  
	 * @param dpId		id of {@link Datapoint}
	 * @param username  id of a user
	 * @param token  authentication token generated while the user logs in
	 * @return		Response 204 if parameter value is null or if measure with specified mid doesnt exist<br />
	 * 				Response 401 if user token is not valid<br />
	 * 				Response 200 with {@link Measure} detail information<br /> 
	 */
	@DELETE
	@Path("history/{dpId}/del")
	public Response deleteHistory(@PathParam("dpId")long dpId, @QueryParam("username")String username, @QueryParam("access_token")String token){
		if(dpId == 0)
			return Response.noContent().build();
		
		if(!authAdapter.isAuthenticated(username, token)){
			 return Response.status(401).build();
		}
		
		return Response.ok(adapter.deleteHistory(dpId)).build();
	}	
	
	/**
	 *  get list of suggested measures
	 *  
	 * @return list of measures List<String>
	 */
	@GET 
	@Path("suggested")
	public List<String> getSuggestedMeasures(){
		return adapter.getSuggestedMeasures();
	}	
}
