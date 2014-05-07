package it.unitn.lifecoach.model;

/**
 *
 * Represents a nutrient object. It contains the values of this nutrient of a specific product. 
 * The nutrients are retrieved from http://api.foodessentials.com/
 *<p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nutrient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nutrientFeLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nutrientName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nutrientUom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nutrientValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * @author User
 *
 */
public class Nutrient {
	
	private String nutrientName;
	private String nutrientValue;
	private String nutrientUom;
	private String nutrientFeLevel;

	public Nutrient(){
		
	}
	
	public Nutrient(String name, String value, String uom, String fe_level){		
		this.nutrientName=name;
		this.nutrientValue=value;
		this.nutrientUom=uom;
		this.nutrientFeLevel =fe_level;
	}

	public String getNutrientName() {
		return nutrientName;
	}

	public void setNutrientName(String nutrientName) {
		this.nutrientName = nutrientName;
	}

	public String getNutrientValue() {
		return nutrientValue;
	}

	public void setNutrientValue(String nutrientValue) {
		this.nutrientValue = nutrientValue;
	}

	public String getNutrientUom() {
		return nutrientUom;
	}

	public void setNutrientUom(String nutrientUom) {
		this.nutrientUom = nutrientUom;
	}

	public String getNutrientFeLevel() {
		return nutrientFeLevel;
	}

	public void setNutrientFeLevel(String nutrientFeLevel) {
		this.nutrientFeLevel = nutrientFeLevel;
	}
	
}
