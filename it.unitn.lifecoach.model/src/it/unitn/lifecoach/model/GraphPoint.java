/**
 * 
 */
package it.unitn.lifecoach.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Elvis Koci <elvkoci@gmail.com>
 * @author Kemele Muhammed Endris
 *
 */
@XmlRootElement
@XmlType(name = "graph_point", propOrder = {
		"x",
		"y"})
public class GraphPoint {
	
		
		private String y;
		private String x;
		
		public GraphPoint(){
			
		}
		
		public GraphPoint(String x, String y){
			
			this.x=x;
			this.y=y;
		}

		public String getY() {
			return y;
		}

		public void setY(String y) {
			this.y = y;
		}

		public String getX() {
			return x;
		}

		public void setX(String x) {
			this.x = x;
		}
}
