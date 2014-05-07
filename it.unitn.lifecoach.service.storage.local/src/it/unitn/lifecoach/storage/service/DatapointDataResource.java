/**
 * 
 */
package it.unitn.lifecoach.storage.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.storage.dao.DatapointDAO;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/datapointdata")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class DatapointDataResource {

	private DatapointDAO dpdao = new DatapointDAO();
	/**
	 * 
	 * @param dp
	 * @return
	 */
	@POST
	@Path("add")
	public Datapoint addDatapoint(Datapoint dp){
		try{
			return dpdao.add(dp);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param dpId
	 * @param dp
	 * @return
	 */
	@PUT
	@Path("{dpId}/update")
	public Datapoint updateDatapoint(@PathParam("dpId")long dpId, Datapoint dp){
		try{
			return dpdao.update(dp);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param dpId
	 * @return
	 */
	@DELETE
	@Path("{dpId}/del")
	public String deleteDatapoint(@PathParam("dpId")long dpId){
		try{
			boolean st = dpdao.delete(dpId);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting Datapoint\"}";
		}
	}
	/**
	 * 
	 * @param dpId
	 * @return
	 */
	@GET
	@Path("{dpId}")
	public Datapoint getDatapoint(@PathParam("dpId")long dpId){
		try{
			return dpdao.get(dpId);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param measureId
	 * @return
	 */
	@GET
	@Path("{measureId}/measure")
	public List<Datapoint> getMeasureDatapoints(@PathParam("measureId")long measureId){
		try{
			return dpdao.getAllMeasureDatapoints(measureId);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param goalId
	 * @return
	 */
	@GET
	@Path("{goalId}/all")
	public List<Datapoint> getGoalDatapoints(@PathParam("goalId")long goalId){
		try{
			return dpdao.getAllGoalDatapoints(goalId);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("all")
	public List<Datapoint> getAllDatapoints(){
		try{
			return dpdao.getAll();
		}catch(Exception e){
			return null;
		}
	}
}
