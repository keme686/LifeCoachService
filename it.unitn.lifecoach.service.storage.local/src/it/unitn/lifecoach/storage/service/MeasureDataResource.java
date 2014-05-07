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

import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.storage.dao.MeasureDAO;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@Path("/measuredata")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
public class MeasureDataResource {

	private MeasureDAO msdao = new MeasureDAO();
	
	/**
	 * 
	 * @param measure
	 * @return
	 */
	@POST
	@Path("add")
	public Measure addMeasure(Measure measure){
		try{
			measure = msdao.addMeasure(measure);
			return measure;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param mid
	 * @param measure
	 * @return
	 */
	@PUT
	@Path("{mid}/update")
	public Measure updateMeasure(@PathParam("mid")long mid, Measure measure){
		try{
			measure = msdao.update(measure);
			return measure;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param mid
	 * @return
	 */
	@DELETE
	@Path("{mid}/del")
	public String deleteMeasure(@PathParam("mid")long mid){
		try{
			boolean st = msdao.delete(mid);
			return "{\"deletion\":"+ st + "}";
		}catch(Exception e){
			return "{\"Error\":\"Error while deleting measure\"}";
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("{mid}")
	public Measure getMeasure(@PathParam("mid")long id){
		try{
			return msdao.get(id);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("all")
	public List<Measure> getAllMeasures(){
		try{
			return msdao.getAll();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @param username
	 * @return
	 */
	@GET
	@Path("{username}/all")
	public List<Measure> getAllMeasures(@PathParam("username")String username){
		try{
			return msdao.getAll(username);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
