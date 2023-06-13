/**
 * 
 */
package org.tedros.person.module.company.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TCellValueFactory;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.builder.TEditModelRowFactoryCallBackBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.tablecell.TShortDateCallback;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.ejb.controller.IPersonStatusController;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.LegalPersonMV;
import org.tedros.person.model.LegalStatus;
import org.tedros.person.model.LegalStatusMV;
import org.tedros.person.model.LegalType;
import org.tedros.person.model.LegalTypeMV;
import org.tedros.person.module.company.table.EmployeeITemMV;
import org.tedros.person.module.company.table.StaffTypeCellCallBack;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IPersonController.JNDI_NAME, model=LegalPerson.class)
@TListViewPresenter(
	page=@TPage(serviceName = IPersonController.JNDI_NAME,
	query = @TQuery(entity=LegalPerson.class, condition= {
			@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.CORPORATE_NAME),
			@TCondition(field = "otherName", operator=TCompareOp.LIKE, label=TUsualKey.TRADE_NAME)},
		orderBy= {@TOrder(label = TUsualKey.CORPORATE_NAME, field = "name"),
				@TOrder(label = TUsualKey.TRADE_NAME, field = "otherName")}
			),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=PersonKeys.VIEW_LEGAL_PERSON , buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false, saveAllModels=false, saveOnlyChangedModels=false)))
@TSecurity(id=DomainApp.LEGAL_PERSON_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_LEGAL_PERSON, viewName = PersonKeys.VIEW_LEGAL_PERSON,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CompanyMV extends LegalPersonMV<LegalPerson> {

	@TTabPane(tabs = { 
		@TTab(text = TUsualKey.MAIN_DATA,
			content = @TContent(detailForm=@TDetailForm(fields={"otherName","type", "address"}))),  
		@TTab(text = TUsualKey.STAFF,
			content = @TContent(detailForm=@TDetailForm(fields={"staff"}))),
		@TTab(text = TUsualKey.DESCRIPTION, 
			content = @TContent(detailForm=@TDetailForm(fields={"description"}))),
		@TTab(text = TUsualKey.OBSERVATION, 
			content = @TContent(detailForm=@TDetailForm(fields={"observation"}))), 
		@TTab(text = TUsualKey.EVENTS,
		content = @TContent(detailForm=@TDetailForm(fields={"events"})))
	})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(firstItemText=TUsualKey.SELECT,
	process=@TProcess(service = IPersonTypeController.JNDI_NAME, 
	modelView=LegalTypeMV.class, query=@TQuery(entity=LegalType.class)))
	@THBox(	 spacing=10, fillHeight=true,
	pane=@TPane(children={"type", "status", "startActivities", "endActivities"}),
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER), 
			@TPriority(field="startActivities", priority=Priority.NEVER), 
			@TPriority(field="endActivities", priority=Priority.NEVER)}))
	protected SimpleObjectProperty<LegalType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(
	process=@TProcess(service = IPersonStatusController.JNDI_NAME, 
	modelView=LegalStatusMV.class, query=@TQuery(entity=LegalStatus.class)))
	private SimpleObjectProperty<LegalStatus> status;
	
	@TLabel(text=TUsualKey.START_ACTIVITIES)
	@TDatePickerField
	protected SimpleObjectProperty<Date> startActivities;
	
	@TLabel(text=TUsualKey.END_ACTIVITIES)
	@TDatePickerField
	protected SimpleObjectProperty<Date> endActivities;
	
	@TTableView(editable=true, rowFactory=TEditModelRowFactoryCallBackBuilder.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
			@TTableColumn(cellValue="toStringProperty", text = TUsualKey.NAME, prefWidth=20, resizable=true), 
			@TTableColumn(cellValue="type", text = TUsualKey.OCCUPATION, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=StaffTypeCellCallBack.class))), 
			@TTableColumn(cellValue="hiringDate", text = TUsualKey.HIRING_DATE, resizable=true,
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))), 
			@TTableColumn(cellValue="resignationDate", text = TUsualKey.RESIGNATION_DATE, resizable=true,
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))),
	})
	@TGenericType(model = Employee.class, modelView=EmployeeITemMV.class)
	protected ITObservableList<EmployeeITemMV> staff;
	public CompanyMV(LegalPerson entity) {
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

	public SimpleObjectProperty<LegalType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<LegalType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<LegalStatus> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<LegalStatus> status) {
		this.status = status;
	}

	public SimpleObjectProperty<Date> getStartActivities() {
		return startActivities;
	}

	public void setStartActivities(SimpleObjectProperty<Date> startActivities) {
		this.startActivities = startActivities;
	}

	public SimpleObjectProperty<Date> getEndActivities() {
		return endActivities;
	}

	public void setEndActivities(SimpleObjectProperty<Date> endActivities) {
		this.endActivities = endActivities;
	}

	public ITObservableList<EmployeeITemMV> getStaff() {
		return staff;
	}

	public void setStaff(ITObservableList<EmployeeITemMV> staff) {
		this.staff = staff;
	}

}
