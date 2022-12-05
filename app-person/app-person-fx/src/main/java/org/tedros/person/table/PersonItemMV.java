/**
 * 
 */
package org.tedros.person.table;

import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.model.Person;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class PersonItemMV extends TEntityModelView<Person> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty name;
	
	
	public PersonItemMV(Person entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

}
