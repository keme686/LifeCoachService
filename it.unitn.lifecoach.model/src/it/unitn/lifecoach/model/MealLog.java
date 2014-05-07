package it.unitn.lifecoach.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the MealLog database table.
 * 
 */
@Entity
@Table(name="MealLog")
@XmlRootElement
public class MealLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	/**
	 * calories gained from this meal log
	 */
	private int calories;

	/**
	 * name of the meal to log
	 */
	private String name;

	/**
	 * size of the meal served
	 */
	@Column(name="serving_size")
	private double servingSize;

	/**
	 * unit of meal size measured, e.g., cup, mg, ml, serving, ...
	 */
	@Column(name="serving_size_unit")
	private String servingSizeUnit;

	/**
	 * serving time of this meal. e.g., Breakfase, morning snack, lunch, afternoon snack, dinner, evening snack
	 */
	@Column(name="serving_time")
	private String servingTime;

	/**
	 * type of meal.e.g., vegetable(green), high fat, ...
	 */
	@Column(name="type_of_meal")
	private String typeOfMeal;

	/**
	 * updated time stored by system
	 */
	@Column(name="updated_time")
	private long updatedTime;

	/**
	 * user associated with this meal log
	 */
	//bi-directional many-to-one association to User
	@XmlTransient
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public MealLog() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCalories() {
		return this.calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getServingSize() {
		return this.servingSize;
	}

	public void setServingSize(double servingSize) {
		this.servingSize = servingSize;
	}

	public String getServingSizeUnit() {
		return this.servingSizeUnit;
	}

	public void setServingSizeUnit(String servingSizeUnit) {
		this.servingSizeUnit = servingSizeUnit;
	}

	public String getServingTime() {
		return this.servingTime;
	}

	public void setServingTime(String servingTime) {
		this.servingTime = servingTime;
	}

	public String getTypeOfMeal() {
		return this.typeOfMeal;
	}

	public void setTypeOfMeal(String typeOfMeal) {
		this.typeOfMeal = typeOfMeal;
	}

	public long getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}