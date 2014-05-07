/**
 * 
 */
package it.unitn.lifecoach.service.logic.selfmonitoring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.model.User;
import it.unitn.lifecoach.service.storage.local.adapter.DatapointDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.MeasureDataAdapter;
import it.unitn.lifecoach.service.storage.local.adapter.UserDataAdapter;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/measurelogic")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class MeasureResource {
	
	/**
	 * Measure data adapter which users jersey client adapter to call the Data storage REST service
	 */
	MeasureDataAdapter adapter = new MeasureDataAdapter();
	/**
	 * Datapoint data adapter which users jersey client adapter to call the Data storage REST service
	 */
	DatapointDataAdapter dpAdapter = new DatapointDataAdapter();
	/**
	 * User data adapter which users jersey client adapter to call the Data storage REST service
	 */
	UserDataAdapter userAdapter = new UserDataAdapter();
	
	/**
	 * create a new Data Measure for a user
	 * 
	 * @param measure
	 * @return
	 */	
	@POST
	@Path("{username}")
	public Measure addMeasure(@PathParam("username")String username, Measure measure){
		User u = userAdapter.getUser(username);
		if(measure ==null || u == null)
			return null;
		
		measure.setUser(u);
		measure = adapter.addMeasure(measure);
		
		Datapoint dpoint = new Datapoint();
		dpoint.setComment("measure added on " + new Date());
		dpoint.setGoal(null);
		dpoint.setMeasure(measure);
		dpoint.setTimestamp(measure.getUpdateTime());
		dpoint.setUpdatedTime(measure.getUpdateTime());
		dpoint.setValue(measure.getValue());
		dpoint.setValueUnit(measure.getValueUnit());
		
		dpAdapter.addDatapoint(dpoint);   //add measure history
		
		return measure;
	}
	/**
	 * update measure data, except name of a measure.
	 * 
	 * @param measure
	 * @param mid
	 * @return
	 */
	@PUT
	@Path("{username}/{mid}")
	public Measure updateMeasure(Measure measure, @PathParam("mid") long mid, @PathParam("username")String username){
		
		Measure m = adapter.getMeasure(mid);
		User u = userAdapter.getUser(username);
		
		if(m == null || u == null )
			return null;		
		measure.setUser(u);
		measure.setName(m.getName());		
		measure =adapter.updateMeasure(measure, mid);
		if(measure == null) return null;
		
		Datapoint dpoint = new Datapoint();
		dpoint.setComment("Measure updated on " + new Date());
		
		dpoint.setGoal(null);
		dpoint.setMeasure(measure);
		dpoint.setTimestamp(measure.getUpdateTime());
		dpoint.setUpdatedTime(measure.getUpdateTime());
		dpoint.setValue(measure.getValue());
		dpoint.setValueUnit(measure.getValueUnit());
		dpAdapter.addDatapoint(dpoint);   //add measure history
		return measure;
	}
	/**
	 * get measure information
	 * 
	 * @param mid
	 * @return
	 */
	@GET
	@Path("{mid}")
	public Measure getMeasure(@PathParam("mid")long mid){
		return adapter.getMeasure(mid);
	}
	/**
	 * delete measure information including datapoints associated with it.
	 * this operation can be done if this measure is not associated to a goal
	 * 
	 * @param mid
	 * @return
	 */
	@DELETE
	@Path("{username}/{mid}")
	public String deleteMeasure(@PathParam("mid")long mid, @PathParam("username")String username){
		Measure m = adapter.getMeasure(mid);
		User u = userAdapter.getUser(username);
		
		if(m == null || u == null || !u.equals(m.getUser()))
			return null;		
		
		return adapter.deleteMeasure(mid);
	}
	
	/**
	 * gets all measures associated with this username
	 *  
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/all")
	public List<Measure> getAll(@PathParam("username")String username){
		return adapter.getAll(username);
	}
	
	/**
	 *  gets list of datapoints as a History for this measure mId
	 *   
	 * @param mId
	 * @return
	 */
	@GET
	@Path("{mid}/history")
	public List<Datapoint> getHistory(@PathParam("mid")long mId){
		return dpAdapter.getAll(mId);
	}
	/**
	 * update measure datapoint (history) identified by dpId
	 * 
	 * @param dpId
	 * @param dpoint
	 * @return
	 */
	@PUT
	@Path("history/{dpId}/update")
	public Datapoint updateHistory(@PathParam("dpId")long dpId, Datapoint dpoint){
		Datapoint dp = dpAdapter.getDatapoint(dpId);
		if(dp == null) return null;
		dpoint.setMeasure(dp.getMeasure());
		dpoint.setGoal(dp.getGoal());
		dpoint.setUpdatedTime(new Date().getTime());
		
		return dpAdapter.updateDatapoint(dpoint, dpId);
	}
	
	/**
	 *  delete measure history
	 *  
	 * @param dpId
	 * @return
	 */
	@DELETE
	@Path("history/{dpId}/del")
	public String deleteHistory(@PathParam("dpId")long dpId){
		return dpAdapter.deleteDatapoint(dpId);
	}	
	
	/**
	 *  get list of suggested measures
	 *  
	 * @return list of measures List<String>
	 */
	@GET 
	@Path("suggested")
	public List<String> getSuggestedMeasures(){
		List<String> sms = new ArrayList<>();
		sms.add("weight");
		sms.add("height");
		sms.add("sleep");
		sms.add("step");
		sms.add("blood pressure");
		sms.add("heart rate");
		sms.add("body fat");
		
		return sms;
	}	
	
}
