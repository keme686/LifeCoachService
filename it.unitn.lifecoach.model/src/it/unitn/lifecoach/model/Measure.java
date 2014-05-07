package it.unitn.lifecoach.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the Measure database table.
 * 
 */
@Entity
@Table(name="Measure")
@XmlRootElement
public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	/**
	 * name of a measure, e.g., weight, height, ....
	 */
	private String name;
	
	/**
	 * value of a measure
	 */
	private double value;
	
	/**
	 * unit of measure, e.g., kg, ml, steps, hours, ....
	 */
	@Column(name="value_unit")
	private String valueUnit;
	
	/**
	 * datapoints for this measure
	 */
	//bi-directional many-to-one association to Datapoint
/*	@XmlTransient
	@OneToMany(mappedBy="measure")
	private List<Datapoint> datapoints;*/
	
	/**
	 * goals associated with this measure
	 */
	//bi-directional many-to-one association to Goal
/*	@XmlTransient
	@OneToMany(mappedBy="measure")
	private List<Goal> goals;*/

	/**
	 * a user for whose this measure is for
	 */
	//bi-directional many-to-one association to User
	
	@ManyToOne
	@JoinColumn(name="username")
	@XmlTransient
	private User user;

	/**
	 * updated time stored by the system
	 */
	@Column(name="update_time") 
	private long updateTime;
	
	
	public Measure() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getValueUnit() {
		return this.valueUnit;
	}

	public void setValueUnit(String valueUnit) {
		this.valueUnit = valueUnit;
	}

/*	public List<Datapoint> getDatapoints() {
		return this.datapoints;
	}

	public void setDatapoints(List<Datapoint> datapoints) {
		this.datapoints = datapoints;
	}*/

	/*public Datapoint addDatapoint(Datapoint datapoint) {
		getDatapoints().add(datapoint);
		datapoint.setMeasure(this);

		return datapoint;
	}

	public Datapoint removeDatapoint(Datapoint datapoint) {
		getDatapoints().remove(datapoint);
		datapoint.setMeasure(null);

		return datapoint;
	}*/

/*	public List<Goal> getGoals() {
		return this.goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public Goal addGoal(Goal goal) {
		getGoals().add(goal);
		goal.setMeasure(this);

		return goal;
	}

	public Goal removeGoal(Goal goal) {
		getGoals().remove(goal);
		goal.setMeasure(null);

		return goal;
	}*/

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updatedTime) {
		this.updateTime = updatedTime;
	}

}