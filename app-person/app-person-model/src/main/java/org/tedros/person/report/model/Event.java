/**
 * 
 */
package org.tedros.person.report.model;

import java.io.Serializable;
import java.util.Date;

import org.tedros.person.model.PersonEvent;

/**
 * @author Davis Gordon
 *
 */
public class Event implements Serializable {

	private static final long serialVersionUID = -6322318855685739608L;
	
	private String name;
	private String description;
	private Date date;
	
	public Event(PersonEvent e) {
		this.name = e.getName();
		this.description = e.getDescription();
		this.date = e.getInsertDate();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
