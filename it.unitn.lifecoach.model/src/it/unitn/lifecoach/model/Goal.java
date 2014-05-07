package it.unitn.lifecoach.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



/**
 * The persistent class for the Goal database table.
 * 
 */
@Entity
@Table(name="Goal")
@XmlRootElement
public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	/**
	 * planned date to achieve this goal
	 */
	@Column(name="goal_date")
	private long goalDate;

	/**
	 * unique name of this goal, this can be used as a path parameter to query for this goal
	 */
	@Column(name="goal_tag")
	private String goalTag;

	/**
	 * type of a goal. e.g., daily, bounded(short term), long term 
	 */
	@Column(name="goal_type")
	private String goalType;

	/**
	 * planned value to achieve 
	 */
	@Column(name="goal_value")
	private double goalValue;

	/**
	 * unit of measure for goal value 
	 */
	@Column(name="goal_value_unit")
	private String goalValueUnit;

	/**
	 * initial value for this goal
	 */
	@Column(name="initial_value")
	private double initialValue;

	/**
	 * initial value unit of measure
	 */
	@Column(name="initial_value_unit")
	private String initialValueUnit;

	/**
	 * direction of plan, increase (1) or decrease (-1)
	 */
	@Column(name="planned_direction")
	private int plannedDirection;

	/**
	 * rate of increase or decrease
	 */
	private double rate;

	/**
	 * type of rate, e.g., intensive, moderate, easy
	 */
	@Column(name="rate_type")
	private String rateType;

	/**
	 * unit of measure for rate of increase of decrease
	 */
	@Column(name="rate_unit")
	private String rateUnit;

	/**
	 * status of the goal, e.g., Active, Achieved, Lost, Canceled,...
	 */
	private String status;

	/**
	 * title of the goal
	 */
	private String title;

	/**
	 * updated time stored by system
	 */
	@Column(name="updated_time")
	private long updatedTime;

	/**
	 * list of datapoints associated with this goal
	 */
	//bi-directional many-to-one association to Datapoint
	/*@OneToMany(mappedBy="goal")
	private List<Datapoint> datapoints;*/

	/**
	 * owner of this goal
	 */
	//bi-directional many-to-one association to User
	@XmlTransient
	@ManyToOne
	@JoinColumn(name="username")
	private User user;

	/**
	 * measure associated with this goal, if any
	 */
	//bi-directional many-to-one association to Measure
	@ManyToOne
	@JoinColumn(name="measureId")
	private Measure measure;

	public Goal() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGoalDate() {
		return this.goalDate;
	}

	public void setGoalDate(long goalDate) {
		this.goalDate = goalDate;
	}

	public String getGoalTag() {
		return this.goalTag;
	}

	public void setGoalTag(String goalTag) {
		this.goalTag = goalTag;
	}

	public String getGoalType() {
		return this.goalType;
	}

	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}

	public double getGoalValue() {
		return this.goalValue;
	}

	public void setGoalValue(double goalValue) {
		this.goalValue = goalValue;
	}

	public String getGoalValueUnit() {
		return this.goalValueUnit;
	}

	public void setGoalValueUnit(String goalValueUnit) {
		this.goalValueUnit = goalValueUnit;
	}

	public double getInitialValue() {
		return this.initialValue;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public String getInitialValueUnit() {
		return this.initialValueUnit;
	}

	public void setInitialValueUnit(String initialValueUnit) {
		this.initialValueUnit = initialValueUnit;
	}

	public int getPlannedDirection() {
		return this.plannedDirection;
	}

	public void setPlannedDirection(int plannedDirection) {
		this.plannedDirection = plannedDirection;
	}

	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getRateType() {
		return this.rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getRateUnit() {
		return this.rateUnit;
	}

	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}

	/*public List<Datapoint> getDatapoints() {
		return this.datapoints;
	}

	public void setDatapoints(List<Datapoint> datapoints) {
		this.datapoints = datapoints;
	}

	public Datapoint addDatapoint(Datapoint datapoint) {
		getDatapoints().add(datapoint);
		datapoint.setGoal(this);

		return datapoint;
	}

	public Datapoint removeDatapoint(Datapoint datapoint) {
		getDatapoints().remove(datapoint);
		datapoint.setGoal(null);

		return datapoint;
	}*/

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Measure getMeasure() {
		return this.measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

}