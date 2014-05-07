/**
 * 
 */
package prova;

import java.util.List;

import it.unitn.lifecoach.model.Measure;
import it.unitn.lifecoach.storage.dao.MeasureDAO;

/**
 * @author kemele
 *
 */
public class Prova {

	public static void main(String a[]){
		try{
			//UserDAO uda = UserDAO.newInstance();
			MeasureDAO mdo = MeasureDAO.newInstance();
			List<Measure> rt = mdo.getAll();
			for(Measure u: rt)			
			System.out.println("Retrieved User: " + u.getName());			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
