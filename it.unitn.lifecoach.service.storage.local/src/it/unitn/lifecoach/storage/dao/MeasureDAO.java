/**
 * 
 */
package it.unitn.lifecoach.storage.dao;

import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.model.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
public class MeasureDAO {

	/**
	 *  entity manager object
	 */
	private EntityManager em;
	/**
	 * MeasureDAO singleton object 
	 */
	private static MeasureDAO measuredao = new MeasureDAO();
	/**
	 * constructor: initializes entity manager
	 */
	public MeasureDAO() {
		em = Persistence.createEntityManagerFactory("it.unitn.lifecoach.model").createEntityManager();
	}
	public EntityManager getEntityManger(){
		return em;
	}
	/**
	 *  Creates a new Singleton instance
	 * @return a singleton {@link MeasureDAO} object
	 */
	public static MeasureDAO newInstance(){
		return measuredao;
	}
	/**
	 * closes a connection to database set to entity manager {@link em}
	 */
	public void closeConnection(){
		  em.close();
	}
	/**
	 * saves a new Measure
	 * 
	 * @param measure
	 * @return just saved Measure object
	 * @throws Exception
	 */
	public Measure addMeasure(Measure measure) throws Exception{				
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(measure);
		tx.commit();
		return measure;
	}
	/**
	 * updated existing Measure data
	 * 
	 * @param measure
	 * @return
	 * @throws Exception
	 */
	public Measure update(Measure measure) throws Exception{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(measure);
		tx.commit();		
		return measure;
	}
	/**
	 * deletes a measure identified by id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(long id) throws Exception{
		Measure m = em.find(Measure.class, id);
		if(m==null) return true;
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();		
		em.remove(m);
		tx.commit();
		return true;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Measure get(long id) throws Exception{
		Measure m = em.find(Measure.class, id);
		return m;
	}
	/**
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public List<Measure> getAll(String username) throws Exception{
		User u = em.find(User.class, username);
		List<Measure> mes = em.createQuery("SELECT m FROM Measure m WHERE m.user=:username", Measure.class).setParameter("username", u).getResultList();
		return mes;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Measure> getAll() throws Exception{
		List<Measure> mes = em.createQuery("SELECT m FROM Measure m", Measure.class).getResultList();
		return mes;
	}
}
