package it.unitn.lifecoach.model;

import it.unitn.lifecoach.model.Nutrient;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a product score object. This object contains information 
 * such as list of nutrient and their values. Also it contains product score,
 * which is relative measure of how healthy is the food.
 * The product score is retrieved from http://api.foodessentials.com/
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="productScore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nutrients" type="{http://ws.food.adapter.isde/}nutrient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="productScore" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="upc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *  
 * @author User
 *
 */

@XmlRootElement
@XmlType(name = "productScore", propOrder = {
	"upc",
	"nutrients",
    "productScore"
})
public class ProductScore {
	
	private String upc;
	private ArrayList<Nutrient> nutrients;
	private long productScore;
	
	public ProductScore(){
		
	}
	
	public ProductScore(String upc, ArrayList<Nutrient> nutrients ,long product_score ){
		
		this.upc = upc;
		this.nutrients=nutrients;
		this.productScore=product_score;
	}
	
	/**
	 * Gets the list of nutrients
	 * @return
	 */
	public ArrayList<Nutrient> getNutrients() {
		return nutrients;
	}

	/**
	 * Sets the list of nutrients
     *  
	 * @param nutrients
	 */
	public void setNutrients(ArrayList<Nutrient> nutrients) {
		this.nutrients = nutrients;
	}
	
    /**
     * Gets the value of the productScore property.
     * 
     */
    public long getProductScore() {
        return productScore;
    }

    /**
     * Sets the value of the productScore property.
     * 
     */
    public void setProductScore(long value) {
        this.productScore = value;
    }

    /**
     * Gets the value of the upc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpc() {
        return upc;
    }

    /**
     * Sets the value of the upc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpc(String value) {
        this.upc = value;
    }
}

