package org.tedros.person.module.report.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.THRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TVRadioGroup;
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
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.TReportRowFactoryCallBackBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;
import org.tedros.fx.presenter.report.decorator.TDataSetReportDecorator;
import org.tedros.person.PersonKeys;
import org.tedros.person.converter.GenderConverter;
import org.tedros.person.converter.SexConverter;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.domain.Gender;
import org.tedros.person.domain.Sex;
import org.tedros.person.ejb.controller.ILegalPersonController;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.StaffType;
import org.tedros.person.module.company.model.CompanyMV;
import org.tedros.person.module.company.model.StaffTypeMV;
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
	@THRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = SexConverter.class),
		radio = { @TRadio(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE ),
				@TRadio(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE )
		})
	@THBox(	pane=@TPane(children={"sex", "gender"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="sex", priority=Priority.ALWAYS), 
			@TPriority(field="gender", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Sex> sex;
	
	@TLabel(text=TUsualKey.GENDER)
	@THRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = GenderConverter.class),
		radio = { @TRadio(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE),
				@TRadio(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE),
				@TRadio(text = TUsualKey.NEUTER, userData = TUsualKey.NEUTER),
				@TRadio(text = TUsualKey.COMMON, userData = TUsualKey.COMMON)
		})
	private SimpleObjectProperty<Gender> gender;

	@TLabel(text=TUsualKey.OCCUPATION)
	@TComboBoxField(
	process=@TProcess(service = IPersonTypeController.JNDI_NAME, 
	modelView=StaffTypeMV.class, query=@TQuery(entity=StaffType.class)))
	@THBox(	pane=@TPane(children={"type", "employer"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="employer", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<StaffType> type;
	
	@TLabel(text=TUsualKey.EMPLOYER)
	@TComboBoxField(
	process=@TProcess(service = ILegalPersonController.JNDI_NAME, 
	modelView=CompanyMV.class, query=@TQuery(entity=LegalPerson.class)))
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
	@TVRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radio = {@TRadio(text=TUsualKey.NAME, userData="e.name"),  
					@TRadio(text=TUsualKey.OCCUPATION, userData="t.name"), 
					@TRadio(text=TUsualKey.EMPLOYER, userData="a.name"), 
					@TRadio(text=TUsualKey.SEX, userData="e.sex")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text=TFxKey.SORT_TYPE)
	@TVRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radio = {@TRadio(text=TFxKey.SORT_BY_ASC, userData="asc"), 
					@TRadio(text=TFxKey.SORT_BY_DESC, userData="desc")
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
	@TGenericType(model=EmployeeItemModel.class, modelView=EmployeeItemMV.class)
	private ITObservableList<EmployeeItemMV> result;
	
	public EmployeeReportMV(EmployeeReportModel entidade) {
		super(entidade);
	}


}
