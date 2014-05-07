package it.unitn.lifecoach.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a quote object, which contains the text and the name of the author of the quote.
 * All the quotes are retrieved from the Quotes API of www.stands4.com
 * 
 * @author elvis
 *
 */
@XmlRootElement
@XmlType(name = "quote", propOrder = {
	"text",
	"author",
})
public class Quote {
	
	private String text="";
	private String author="";
	
	public Quote(){
		
	}
	
	public Quote(String quote, String author)
	{
		this.text=quote;
		this.author=author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
