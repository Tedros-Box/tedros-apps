package com.tedros.person.module.report.model;

import java.util.Date;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.TFxKey;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TRadioButton;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TReportProcess;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.module.report.action.SearchAction;
import com.tedros.person.converter.GenderConverter;
import com.tedros.person.converter.SexConverter;
import com.tedros.person.domain.Gender;
import com.tedros.person.domain.Sex;
import com.tedros.person.ejb.controller.IStaffTypeController;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.model.StaffType;
import com.tedros.person.module.legal.model.FindLegalPersonMV;
import com.tedros.person.module.legal.model.StaffTypeMV;
import com.tedros.person.module.legal.table.EmployeeRowFactoryBuilder;
import com.tedros.person.module.report.process.EmployeeReportProcess;
import com.tedros.person.report.model.EmployeeItemModel;
import com.tedros.person.report.model.EmployeeReportModel;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;


@TForm(name = "#{form.repo.place}", showBreadcrumBar=true, editCssId="")
@TReportProcess(type=EmployeeReportProcess.class, model = EmployeeReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			action=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="#{view.repo.place}"))
@TSecurity(	id=DomainApp.PLACE_REPORT_FORM_ID, 
			appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.repo.place}",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class EmployeeReportMV extends TModelView<EmployeeReportModel>{
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="#{label.filters}", node=@TNode(id="filtro",parse = true), 
							expanded=true, layoutType=TLayoutType.HBOX,
							fields={"title", "orderBy"}),
					@TTitledPane(text="#{label.result}", node=@TNode(id="resultado",parse = true),
						fields={"result"})})	
	private SimpleStringProperty displayText;
	

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
	@THBox(	pane=@TPane(children={"type", "hiringDate", "resignationDate", "employer"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="hiringDate", priority=Priority.ALWAYS), 
			@TPriority(field="resignationDate", priority=Priority.ALWAYS), 
			@TPriority(field="employer", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<StaffType> type;
	
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
	
	
	@TLabel(text="#{label.order.by}:")
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = "#{label.result.order}")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text="#{label.title}", userData="e.title"), 
					@TRadioButton(text="#{label.type}", userData="t.name"), 
					@TRadioButton(text="#{label.country}", userData="c.name"), 
					@TRadioButton(text="#{label.admin.area}", userData="aa.name"), 
					@TRadioButton(text="#{label.city}", userData="ct.name"), 
					@TRadioButton(text="#{label.code}", userData="a.code")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text="#{label.order.type}:")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text="#{label.order.asc}", userData="asc"), 
					@TRadioButton(text="#{label.order.desc}", userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(editable=true, rowFactory=EmployeeRowFactoryBuilder.class,
			control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
			columns = { @TTableColumn(cellValue="title", text = "#{label.title}", prefWidth=20, resizable=true), 
					@TTableColumn(cellValue="type", text = "#{label.type}", resizable=true), 
						@TTableColumn(cellValue="country", text = "#{label.country}", resizable=true), 
						@TTableColumn(cellValue="address", text = "#{label.address}", resizable=true)
			})
	@TModelViewType(modelClass=EmployeeItemModel.class, modelViewClass=EmployeeItemMV.class)
	private ITObservableList<EmployeeItemMV> result;
	
	public EmployeeReportMV(EmployeeReportModel entidade) {
		super(entidade);
	}
	
	
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return this.displayText;
	}

	/**
	 * @return the displayText
	 */
	public SimpleStringProperty getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(SimpleStringProperty displayText) {
		this.displayText = displayText;
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


	public SimpleObjectProperty<Date> getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(SimpleObjectProperty<Date> birthDate) {
		this.birthDate = birthDate;
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
