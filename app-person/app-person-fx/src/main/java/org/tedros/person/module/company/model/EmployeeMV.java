/**
 * 
 */
package org.tedros.person.module.company.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.ejb.controller.IPersonStatusController;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.Employee;
import org.tedros.person.model.EmployeeStatus;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.NaturalPersonMV;
import org.tedros.person.model.StaffType;
import org.tedros.person.module.company.table.CostCenterTV;
import org.tedros.person.trigger.FilterCostCenterTrigger;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Davis Gordon
 *
 */

@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IPersonController.JNDI_NAME, model=Employee.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonController.JNDI_NAME,
		query = @TQuery(entity=Employee.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME),
				@TCondition(field = "lastName", operator=TCompareOp.LIKE, label=TUsualKey.LAST_NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name"),
					@TOrder(label = TUsualKey.LAST_NAME, field = "lastName")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(
			decorator = @TDecorator(viewTitle=PersonKeys.VIEW_EMPLOYEES, buildModesRadioButton=false),
			behavior=@TBehavior(runNewActionAfterSave=false, saveAllModels=false, saveOnlyChangedModels=false)))
@TSecurity(id=DomainApp.EMPLOYEE_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_LEGAL_PERSON, viewName = PersonKeys.VIEW_EMPLOYEES,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class EmployeeMV extends NaturalPersonMV<Employee> {

	@TTabPane(tabs = { 
		@TTab(text = TUsualKey.MAIN_DATA, scroll=true, fields={"lastName","type", "sex", "address"}), 
		@TTab(text = TUsualKey.DESCRIPTION,fields={"description"}),
		@TTab(text = TUsualKey.OBSERVATION,fields={"observation"}), 
		@TTab(text = TUsualKey.EVENTS,fields={"events"})
	})
	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.OCCUPATION)
	@TComboBoxField(
	process=@TProcess(service = IPersonTypeController.JNDI_NAME, 
	modelView=StaffTypeMV.class, query=@TQuery(entity=StaffType.class)))
	@TFlowPane(hgap=HGAP, vgap=VGAP,
	pane=@TPane(children={"type", "status", "hiringDate", "resignationDate", "legalPerson", "costCenter"}))
	private SimpleObjectProperty<StaffType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(
	process=@TProcess(service = IPersonStatusController.JNDI_NAME, 
	modelView=EmployeeStatusMV.class, query=@TQuery(entity=EmployeeStatus.class)))
	private SimpleObjectProperty<EmployeeStatus> status;
	
	@TLabel(text=TUsualKey.HIRING_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> hiringDate;
	
	@TLabel(text=TUsualKey.RESIGNATION_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> resignationDate;
	
	@TLabel(text=TUsualKey.EMPLOYER)
	@TAutoCompleteEntity(required=true, 
	startSearchAt=3, showMaxItems=30,
	service = IPersonController.JNDI_NAME,
	query = @TQuery(entity = LegalPerson.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "otherName", 
			operator=TCompareOp.LIKE)}))
	@TTrigger(type = FilterCostCenterTrigger.class, 
	targetFieldName="costCenter", runAfterFormBuild=true)
	private SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.COST_CENTER)
	@TComboBoxField()
	private SimpleObjectProperty<CostCenterTV> costCenter;
	
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

	public SimpleObjectProperty<EmployeeStatus> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<EmployeeStatus> status) {
		this.status = status;
	}

	/**
	 * @return the costCenter
	 */
	public SimpleObjectProperty<CostCenterTV> getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(SimpleObjectProperty<CostCenterTV> costCenter) {
		this.costCenter = costCenter;
	}

	/**
	 * @return the legalPerson
	 */
	public SimpleObjectProperty<LegalPerson> getLegalPerson() {
		return legalPerson;
	}

	/**
	 * @param legalPerson the legalPerson to set
	 */
	public void setLegalPerson(SimpleObjectProperty<LegalPerson> legalPerson) {
		this.legalPerson = legalPerson;
	}

}
