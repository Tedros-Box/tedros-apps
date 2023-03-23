/**
 * 
 */
package org.tedros.person.table;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.scene.TNode;
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
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, 
		node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty name;
	
	public PersonTV(Person entity) {
		super(entity);
	}
	
	public PersonTV(LegalPerson entity) {
		super(entity);
	}
	
	public PersonTV(NaturalPerson entity) {
		super(entity);
	}
	
	public PersonTV(Employee entity) {
		super(entity);
	}

	public PersonTV(ClientCompany entity) {
		super(entity);
	}

	public PersonTV(Customer entity) {
		super(entity);
	}
	
	public PersonTV(Member entity) {
		super(entity);
	}

	public PersonTV(Voluntary entity) {
		super(entity);
	}

	public PersonTV(Philanthrope entity) {
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
