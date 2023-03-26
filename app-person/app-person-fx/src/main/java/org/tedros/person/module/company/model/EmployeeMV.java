/**
 * 
 */
package org.tedros.person.module.company.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TOneSelectionModal;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.ejb.controller.IPersonStatusController;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.Employee;
import org.tedros.person.model.EmployeeStatus;
import org.tedros.person.model.FindLegalPersonMV;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.NaturalPersonMV;
import org.tedros.person.model.StaffType;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IEmployeeController.JNDI_NAME, model=Employee.class)
@TListViewPresenter(
		paginator=@TPaginator(entityClass = Employee.class, serviceName = IEmployeeController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name"),
				@TOption(text = TUsualKey.LAST_NAME , value = "lastName")}),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_EMPLOYEES,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.EMPLOYEE_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_LEGAL_PERSON, viewName = PersonKeys.VIEW_EMPLOYEES,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class EmployeeMV extends NaturalPersonMV<Employee> {

	@TTabPane(tabs = { 
		@TTab(text = TUsualKey.MAIN_DATA,
			content = @TContent(detailForm=@TDetailForm(fields={"lastName","type", "sex", "address"}))), 
		@TTab(text = TUsualKey.DESCRIPTION,
			content = @TContent(detailForm=@TDetailForm(fields={"description"}))),
		@TTab(text = TUsualKey.OBSERVATION,
			content = @TContent(detailForm=@TDetailForm(fields={"observation"}))), 
		@TTab(text = TUsualKey.EVENTS,
			content = @TContent(detailForm=@TDetailForm(fields={"events"})))
	})
	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.OCCUPATION)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IPersonTypeController.JNDI_NAME, 
	optionModelViewClass=StaffTypeMV.class,
	entityClass=StaffType.class))
	@THBox(	spacing=10, fillHeight=true,
			pane=@TPane(children={"type", "status", "hiringDate", "resignationDate", "employer"}), 
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER), 
			@TPriority(field="hiringDate", priority=Priority.NEVER), 
			@TPriority(field="resignationDate", priority=Priority.NEVER), 
			@TPriority(field="employer", priority=Priority.NEVER)}))
	private SimpleObjectProperty<StaffType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IPersonStatusController.JNDI_NAME, 
	optionModelViewClass=EmployeeStatusMV.class,
	entityClass=EmployeeStatus.class))
	private SimpleObjectProperty<EmployeeStatus> status;
	
	@TLabel(text=TUsualKey.HIRING_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> hiringDate;
	
	@TLabel(text=TUsualKey.RESIGNATION_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> resignationDate;
	
	@TLabel(text=TUsualKey.EMPLOYER)
	@TOneSelectionModal(modelClass = LegalPerson.class, modelViewClass = FindLegalPersonMV.class, 
	width=300, height=50, required=true)
	private SimpleObjectProperty<LegalPerson> employer;
	
	public EmployeeMV(Employee entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleObjectProperty<StaffType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<StaffType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<Date> getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(SimpleObjectProperty<Date> hiringDate) {
		this.hiringDate = hiringDate;
	}

	public SimpleObjectProperty<Date> getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(SimpleObjectProperty<Date> resignationDate) {
		this.resignationDate = resignationDate;
	}

	public SimpleObjectProperty<LegalPerson> getEmployer() {
		return employer;
	}

	public void setEmployer(SimpleObjectProperty<LegalPerson> employer) {
		this.employer = employer;
	}

	public SimpleObjectProperty<EmployeeStatus> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<EmployeeStatus> status) {
		this.status = status;
	}

}
