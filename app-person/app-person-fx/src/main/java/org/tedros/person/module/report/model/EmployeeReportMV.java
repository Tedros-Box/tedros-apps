package org.tedros.person.module.report.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TVerticalRadioGroup;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TReportProcess;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.TReportRowFactoryCallBackBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;
import org.tedros.fx.presenter.report.decorator.TDataSetReportDecorator;
import org.tedros.person.PersonKeys;
import org.tedros.person.converter.GenderConverter;
import org.tedros.person.converter.SexConverter;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.domain.Gender;
import org.tedros.person.domain.Sex;
import org.tedros.person.ejb.controller.ILegalPersonController;
import org.tedros.person.ejb.controller.IStaffTypeController;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.StaffType;
import org.tedros.person.module.legal.model.LegalPersonMV;
import org.tedros.person.module.legal.model.StaffTypeMV;
import org.tedros.person.module.report.action.SearchAction;
import org.tedros.person.module.report.process.EmployeeReportProcess;
import org.tedros.person.module.report.table.EmployeeItemMV;
import org.tedros.person.report.model.EmployeeItemModel;
import org.tedros.person.report.model.EmployeeReportModel;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Priority;


@TForm(name = PersonKeys.FORM_REPORT, showBreadcrumBar=true, editCssId="")
@TReportProcess(type=EmployeeReportProcess.class, model = EmployeeReportModel.class)
@TPresenter(type = TDynaPresenter.class,
	behavior = @TBehavior(type = TDataSetReportBehavior.class, 
		action=SearchAction.class), 
	decorator = @TDecorator(type = TDataSetReportDecorator.class, 
		viewTitle=PersonKeys.VIEW_REPORT_EMPLOYEES))
@TSecurity(	id=DomainApp.EMPLOYEE_REPORT_FORM_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_REPORTS, 
viewName = PersonKeys.VIEW_REPORT_EMPLOYEES,
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class EmployeeReportMV extends TModelView<EmployeeReportModel>{
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
		panes={
			@TTitledPane(text=TUsualKey.FILTERS, node=@TNode(id="filtro",parse = true), 
				expanded=true, layoutType=TLayoutType.HBOX,
				fields={"title", "orderBy"}),
			@TTitledPane(text=TUsualKey.RESULT, node=@TNode(id="resultado",parse = true),
				fields={"result"})})	
	private SimpleLongProperty id;
	
	@TVBox(	pane=@TPane(children={"name", "sex", "type", "interval" }), spacing=10, fillWidth=true,
			vgrow=@TVGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
					@TPriority(field="sex", priority=Priority.ALWAYS), 
					@TPriority(field="type", priority=Priority.ALWAYS), 
					@TPriority(field="interval", priority=Priority.ALWAYS)}))
			
	private SimpleStringProperty title;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120,
		node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name", "lastName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="lastName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.LAST_NAME)
	@TTextField(maxLength=60)
	private SimpleStringProperty lastName;
	
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

	@TLabel(text=TUsualKey.OCCUPATION)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IStaffTypeController.JNDI_NAME, 
	optionModelViewClass=StaffTypeMV.class,
	entityClass=StaffType.class))
	@THBox(	pane=@TPane(children={"type", "employer"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="employer", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<StaffType> type;
	
	@TLabel(text=TUsualKey.EMPLOYER)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = ILegalPersonController.JNDI_NAME, 
	optionModelViewClass=LegalPersonMV.class,
	entityClass=LegalPerson.class))
	private SimpleObjectProperty<LegalPerson> employer;
	
	@THBox(pane=@TPane(children={"birthDate", "hiringDate", "resignationDate"}), spacing=10, fillHeight=true,
		hgrow=@THGrow(priority={@TPriority(field="birthDate", priority=Priority.ALWAYS), 
			@TPriority(field="hiringDate", priority=Priority.ALWAYS), 
			@TPriority(field="resignationDate", priority=Priority.ALWAYS)}))		
	private SimpleStringProperty interval;
	
	@TFieldSet(fields = { "birthDate", "birthDateEnd" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = TUsualKey.BIRTHDATE)
	@TLabel(text=TUsualKey.BEGIN)
	@TDatePickerField
	private SimpleObjectProperty<Date> birthDate;
	
	@TLabel(text=TUsualKey.END)
	@TDatePickerField
	private SimpleObjectProperty<Date> birthDateEnd;

	@TFieldSet(fields = { "hiringDate", "hiringDateEnd" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = TUsualKey.HIRING_DATE)
	@TLabel(text=TUsualKey.BEGIN)
	@TDatePickerField
	private SimpleObjectProperty<Date> hiringDate;

	@TLabel(text=TUsualKey.END)
	@TDatePickerField
	private SimpleObjectProperty<Date> hiringDateEnd;
	
	@TFieldSet(fields = { "resignationDate", "resignationDateEnd" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = TUsualKey.RESIGNATION_DATE)
	@TLabel(text=TUsualKey.BEGIN)
	@TDatePickerField
	private SimpleObjectProperty<Date> resignationDate;
	
	@TLabel(text=TUsualKey.END)
	@TDatePickerField
	private SimpleObjectProperty<Date> resignationDateEnd;
	
	@TLabel(text=TFxKey.SORT_BY)
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend =TUsualKey.RESULT_ORDER)
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=TUsualKey.NAME, userData="e.name"),  
					@TRadioButton(text=TUsualKey.OCCUPATION, userData="t.name"), 
					@TRadioButton(text=TUsualKey.EMPLOYER, userData="a.name"), 
					@TRadioButton(text=TUsualKey.SEX, userData="e.sex")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text=TFxKey.SORT_TYPE)
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=TFxKey.SORT_BY_ASC, userData="asc"), 
					@TRadioButton(text=TFxKey.SORT_BY_DESC, userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(
			selectionModel=@TTableViewSelectionModel(selectionMode=SelectionMode.MULTIPLE,parse = true), 
			rowFactory=TReportRowFactoryCallBackBuilder.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
				@TTableColumn(cellValue="name", text = TUsualKey.NAME, prefWidth=20, resizable=true), 
				@TTableColumn(cellValue="type", text = TUsualKey.OCCUPATION, resizable=true), 
				@TTableColumn(cellValue="employer", text = TUsualKey.EMPLOYER, resizable=true)
			})
	@TModelViewType(modelClass=EmployeeItemModel.class, modelViewClass=EmployeeItemMV.class)
	private ITObservableList<EmployeeItemMV> result;
	
	public EmployeeReportMV(EmployeeReportModel entidade) {
		super(entidade);
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getLastName() {
		return lastName;
	}

	public void setLastName(SimpleStringProperty lastName) {
		this.lastName = lastName;
	}

	public SimpleObjectProperty<Sex> getSex() {
		return sex;
	}

	public void setSex(SimpleObjectProperty<Sex> sex) {
		this.sex = sex;
	}

	public SimpleObjectProperty<Gender> getGender() {
		return gender;
	}

	public void setGender(SimpleObjectProperty<Gender> gender) {
		this.gender = gender;
	}

	public SimpleObjectProperty<StaffType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<StaffType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<LegalPerson> getEmployer() {
		return employer;
	}

	public void setEmployer(SimpleObjectProperty<LegalPerson> employer) {
		this.employer = employer;
	}

	public SimpleStringProperty getInterval() {
		return interval;
	}

	public void setInterval(SimpleStringProperty interval) {
		this.interval = interval;
	}

	public SimpleObjectProperty<Date> getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(SimpleObjectProperty<Date> birthDate) {
		this.birthDate = birthDate;
	}

	public SimpleObjectProperty<Date> getBirthDateEnd() {
		return birthDateEnd;
	}

	public void setBirthDateEnd(SimpleObjectProperty<Date> birthDateEnd) {
		this.birthDateEnd = birthDateEnd;
	}

	public SimpleObjectProperty<Date> getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(SimpleObjectProperty<Date> hiringDate) {
		this.hiringDate = hiringDate;
	}

	public SimpleObjectProperty<Date> getHiringDateEnd() {
		return hiringDateEnd;
	}

	public void setHiringDateEnd(SimpleObjectProperty<Date> hiringDateEnd) {
		this.hiringDateEnd = hiringDateEnd;
	}

	public SimpleObjectProperty<Date> getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(SimpleObjectProperty<Date> resignationDate) {
		this.resignationDate = resignationDate;
	}

	public SimpleObjectProperty<Date> getResignationDateEnd() {
		return resignationDateEnd;
	}

	public void setResignationDateEnd(SimpleObjectProperty<Date> resignationDateEnd) {
		this.resignationDateEnd = resignationDateEnd;
	}

	public SimpleStringProperty getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(SimpleStringProperty orderBy) {
		this.orderBy = orderBy;
	}

	public SimpleStringProperty getOrderType() {
		return orderType;
	}

	public void setOrderType(SimpleStringProperty orderType) {
		this.orderType = orderType;
	}

	public ITObservableList<EmployeeItemMV> getResult() {
		return result;
	}

	public void setResult(ITObservableList<EmployeeItemMV> result) {
		this.result = result;
	}

}
