package it.unitn.lifecoach.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the Datapoint database table.
 * 
 */
@Entity
@Table(name="Datapoint")
@XmlRootElement
public class Datapoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	/**
	 * comment on datapoint
	 */
	private String comment;

	/**
	 * date and time for the datapoint specified by user
	 */
	private long timestamp;

	/**
	 * updated time stored by the system
	 */
	@Column(name="updated_time")
	private long updatedTime;

	/**
	 * the value specified by the user for a measure
	 */
	private double value;

	/**
	 * unit of measure for the value specified
	 */
	@Column(name="value_unit") 
	private String valueUnit;
	/**
	 * a goal this datapoint belongs, if any 
	 */
	//bi-directional many-to-one association to Goal
	@XmlTransient
	@ManyToOne
	private Goal goal;

	/**
	 * a measure this datapoint is for 
	 */
	//bi-directional many-to-one association to Measure
	@XmlTransient
	@ManyToOne
	private Measure measure;

	public Datapoint() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Goal getGoal() {
		return this.goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public Measure getMeasure() {
		return this.measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public String getValueUnit() {
		return valueUnit;
	}

	public void setValueUnit(String valueUnit) {
		this.valueUnit = valueUnit;
	}

}