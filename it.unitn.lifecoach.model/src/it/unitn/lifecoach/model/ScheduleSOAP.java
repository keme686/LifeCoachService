/**
 * 
 */
package it.unitn.lifecoach.model;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * The class for the Schedule SOAP object  
 */
@XmlRootElement
public class ScheduleSOAP implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	
	/**
	 * The title of the schedule (eg.Take medicine XYZ)
	 */
	private String title;
	
	/**
	 * More detailed description of the schedule
	 */
	
	private String desc;
	
	
	/**
	 * The time when the scheduled event starts
	 */
	
	private long startTime;
	
	/**
	 * The time when  the scheduled event ends
	 */

	private long endTime;
	
	/**
	 * The date when the scheduled event ends
	 */

	private String type;
	
	/**
	 * updated time stored by the system
	 */
	private long updatedTime; 

	public ScheduleSOAP(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}
}
