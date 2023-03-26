/**
 * 
 */
package org.tedros.person.table;

import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.model.ClientCompany;
import org.tedros.person.model.Customer;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Member;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.Person;
import org.tedros.person.model.Philanthrope;
import org.tedros.person.model.Voluntary;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */

public class PersonTV extends TEntityModelView<Person> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty name;

	private SimpleStringProperty label;
	
	public PersonTV(Person entity) {
		super(entity);
		init(entity);
	}
	
	public PersonTV(LegalPerson entity) {
		super(entity);
		init(entity);
	}
	
	public PersonTV(NaturalPerson entity) {
		super(entity);
		init(entity);
	}
	
	public PersonTV(Employee entity) {
		super(entity);
		init(entity);
	}

	public PersonTV(ClientCompany entity) {
		super(entity);
		init(entity);
	}

	public PersonTV(Customer entity) {
		super(entity);
		init(entity);
	}
	
	public PersonTV(Member entity) {
		super(entity);
		init(entity);
	}

	public PersonTV(Voluntary entity) {
		super(entity);
		init(entity);
	}

	public PersonTV(Philanthrope entity) {
		super(entity);
		init(entity);
	}

	private void init(Person p) {
		super.registerProperty("label", label);
		label.setValue(p.toString());
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

	public SimpleStringProperty getLabel() {
		return label;
	}

	public void setLabel(SimpleStringProperty label) {
		this.label = label;
	}

}
