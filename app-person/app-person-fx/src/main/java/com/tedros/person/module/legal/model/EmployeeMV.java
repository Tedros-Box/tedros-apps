/**
 * 
 */
package com.tedros.person.module.legal.model;

import java.util.Date;

import com.tedros.docs.export.ModalDocumentMV;
import com.tedros.docs.model.Document;
import com.tedros.extension.contact.model.ContactMV;
import com.tedros.extension.model.Contact;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TRadioButton;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.LocatKey;
import com.tedros.location.model.Address;
import com.tedros.location.module.address.model.AddressMV;
import com.tedros.person.converter.GenderConverter;
import com.tedros.person.converter.SexConverter;
import com.tedros.person.domain.Gender;
import com.tedros.person.domain.Sex;
import com.tedros.person.ejb.controller.IStaffTypeController;
import com.tedros.person.model.Employee;
import com.tedros.person.model.PersonAttributes;
import com.tedros.person.model.StaffType;
import com.tedros.person.module.natural.model.PersonAttributesMV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeMV extends TEntityModelView<Employee> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayProperty;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true,
		node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name", "lastName", "birthDate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="lastName", priority=Priority.ALWAYS), 
			@TPriority(field="birthDate", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.LAST_NAME)
	@TTextField(maxLength=60)
	private SimpleStringProperty lastName;
	
	@TLabel(text=TUsualKey.BIRTHDATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> birthDate;

	@TLabel(text=TUsualKey.OCCUPATION)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
	optionsList=@TOptionsList(serviceName = IStaffTypeController.JNDI_NAME, 
	optionModelViewClass=StaffTypeMV.class,
	entityClass=StaffType.class))
	@THBox(	pane=@TPane(children={"type", "hiringDate", "resignationDate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="hiringDate", priority=Priority.ALWAYS), 
			@TPriority(field="resignationDate", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<StaffType> type;
	
	@TLabel(text=TUsualKey.HIRING_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> hiringDate;
	
	@TLabel(text=TUsualKey.RESIGNATION_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> resignationDate;
	
	@TLabel(text=TUsualKey.SEX)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = SexConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE ),
				@TRadioButton(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE )
		})
	@THBox(	pane=@TPane(children={"sex", "gender"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="sex", priority=Priority.ALWAYS), 
			@TPriority(field="gender", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Sex> sex;
	
	@TLabel(text=TUsualKey.GENDER)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = GenderConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE),
				@TRadioButton(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE),
				@TRadioButton(text = TUsualKey.NEUTER, userData = TUsualKey.NEUTER),
				@TRadioButton(text = TUsualKey.COMMON, userData = TUsualKey.COMMON)
		})
	private SimpleObjectProperty<Gender> gender;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=2000, wrapText=true, prefRowCount=5)
	@THBox(	pane=@TPane(children={"description", "observation"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="description", priority=Priority.ALWAYS), 
			@TPriority(field="observation", priority=Priority.ALWAYS)}))
	private SimpleStringProperty description;
	
	@TLabel(text=TUsualKey.OBSERVATION)
	@TTextAreaField(maxLength=2000, wrapText=true, prefRowCount=5)
	private SimpleStringProperty observation;
	
	@TLabel(text=LocatKey.ADDRESS)
	@TEditEntityModal(modelClass = Address.class, modelViewClass=AddressMV.class)
	@TModelViewType(modelClass = Address.class, modelViewClass=AddressMV.class)
	@THBox(	pane=@TPane(children={"address", "contacts", "documents", "attributes"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="address", priority=Priority.ALWAYS), 
			@TPriority(field="contacts", priority=Priority.ALWAYS), 
			@TPriority(field="attributes", priority=Priority.ALWAYS), 
			@TPriority(field="documents", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<AddressMV> address;
	
	@TLabel(text=TUsualKey.ATTRIBUTES)
	@TEditEntityModal(modelClass = PersonAttributes.class, modelViewClass=PersonAttributesMV.class)
	@TModelViewType(modelClass = PersonAttributes.class, modelViewClass=PersonAttributesMV.class)
	private ITObservableList<PersonAttributesMV> attributes;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(modelClass = Document.class, modelViewClass=ModalDocumentMV.class)
	@TModelViewType(modelClass=Document.class, modelViewClass=ModalDocumentMV.class)
	public ITObservableList<ModalDocumentMV> documents;
	
	public EmployeeMV(Employee entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
	}

}
