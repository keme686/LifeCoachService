	/**
 * 
 */
package it.unitn.lifecoach.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The persistent class for the Schedule Table 
 */
@Entity
@Table(name="schedule")
@XmlRootElement
@XmlType(name = "schedule", propOrder = {
		"id",
		"title",
		"desc",
		"startTime",
	    "endTime",
	    "type",
	    "updatedTime",
	    "user"
	})
public class Schedule implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	/**
	 * The title of the schedule (eg.Take medicine XYZ)
	 */
	private String title;
	
	/**
	 * More detailed description of the schedule
	 */
	@Column(name="schd_desc")
	private String desc;
	
	
	/**
	 * The time when the scheduled event starts
	 */
	@Column(name="start_time") 
	private long startTime;
	
	/**
	 * The time when  the scheduled event ends
	 */
	@Column(name="end_time") 
	private long endTime;
	
	
	/**
	 * Type of the schedule. There will be predefined types ?!
	 */
	@Column(name="schd_type")
	private String type;
	
	/**
	 * updated time stored by the system
	 */
	@Column(name="updated_time") 
	private long updatedTime; 
	
	@XmlTransient
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Schedule(){}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
