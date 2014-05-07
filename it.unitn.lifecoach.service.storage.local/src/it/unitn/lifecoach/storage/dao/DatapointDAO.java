/**
 * 
 */
package it.unitn.lifecoach.storage.dao;

import java.util.ArrayList;
import java.util.List;

import it.unitn.lifecoach.model.Datapoint;
import it.unitn.lifecoach.model.Goal;
import it.unitn.lifecoach.model.Measure;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class DatapointDAO {

	/**
	 *  entity manager object
	 */
	private EntityManager em;
	/**
	 * DatapointDAO singleton object 
	 */
	private static DatapointDAO datapointdao = new DatapointDAO();
	/**
	 * constructor: initializes entity manager
	 */
	public DatapointDAO() {
		em = Persistence.createEntityManagerFactory("it.unitn.lifecoach.model").createEntityManager();
	}
	public EntityManager getEntityManger(){
		return em;
	}
	
	/**
	 *  Creates a new Singleton instance
	 * @return a singleton {@link DatapointDAO} object
	 */
	public static DatapointDAO newInstance(){
		return datapointdao;
	}
	/**
	 * closes a connection to database set to entity manager {@link em}
	 */
	public void closeConnection(){
		  em.close();
	}
	
	/**
	 * Saves a new {@link DatapointDAO} data
	 * 
	 * @param datapoint
	 * @return just saved {@link DatapointDAO} object  
	 * @throws Exception
	 */
	public Datapoint add(Datapoint datapoint) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(datapoint);
		tx.commit();
		return datapoint;
	}
	/**
	 * updates datapoint information 
	 * 
	 * @param datapoint
	 * @return datapoint
	 * @throws Exception
	 */
	public Datapoint update(Datapoint datapoint) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(datapoint);
		tx.commit();
		return datapoint;
	}
	/**
	 * deletes a datapoint information 
	 * 
	 * @param id
	 * @return true/false
	 * @throws Exception
	 */
	public boolean delete(long id) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Datapoint d = em.find(Datapoint.class, id);
		em.remove(d);
		tx.commit();
		return true;
	}
	/**
	 * get a datapoint specified by id
	 * 
	 * @param id
	 * @return datapoint
	 * @throws Exception
	 */
	public Datapoint get(long id) throws Exception {
		Datapoint d = em.find(Datapoint.class, id);
		return d;
	}
	/**
	 * get all datapoints saved for a specified measure
	 * 
	 * @param measureId id of measure
	 * @return
	 * @throws Exception
	 */
	public List<Datapoint> getAllMeasureDatapoints(long measureId) throws Exception{
		Measure m = em.find(Measure.class, measureId);
		if(m==null) return new ArrayList<>();
		List<Datapoint> dts = em.createQuery("SELECT d FROM Datapoint d WHERE d.measure=:measureId", Datapoint.class).setParameter("measureId", m).getResultList();
		return dts;
	}
	/**
	 * get all datapoints for a specified goal
	 * 
	 * @param goalId
	 * @return
	 * @throws Exception
	 */
	public List<Datapoint> getAllGoalDatapoints(long goalId) throws Exception{
		
		
		Goal g = em.find(Goal.class, goalId);
		if(g==null) {
			return new ArrayList<>();
		}
		List<Datapoint> dts = em.createQuery("SELECT d FROM Datapoint d WHERE d.goal=:goalId", Datapoint.class).setParameter("goalId", g).getResultList();
		return dts;
	}
	
	/**
	 * get all datapoints saved in a database
	 * @return
	 * @throws Exception
	 */
	public List<Datapoint> getAll()throws Exception{
		List<Datapoint> dts = em.createQuery("SELECT d FROM Datapoint d", Datapoint.class).getResultList();
		return dts;
	}
}
