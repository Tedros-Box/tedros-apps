package org.tedros.person.module.report.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
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
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.ILegalTypeController;
import org.tedros.person.model.LegalType;
import org.tedros.person.module.legal.model.LegalTypeMV;
import org.tedros.person.module.report.action.SearchAction;
import org.tedros.person.module.report.process.LegalPersonReportProcess;
import org.tedros.person.module.report.table.LegalPersonItemMV;
import org.tedros.person.report.model.LegalPersonItemModel;
import org.tedros.person.report.model.LegalPersonReportModel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;


@TForm(name = PersonKeys.FORM_REPORT, showBreadcrumBar=true, editCssId="")
@TReportProcess(type=LegalPersonReportProcess.class, model = LegalPersonReportModel.class)
@TPresenter(type = TDynaPresenter.class,
	behavior = @TBehavior(type = TDataSetReportBehavior.class, 
		action=SearchAction.class), 
	decorator = @TDecorator(type = TDataSetReportDecorator.class, 
		viewTitle=PersonKeys.VIEW_REPORT_LEGAL_PERSON))
@TSecurity(	id=DomainApp.LEGAL_PERSON_REPORT_FORM_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_REPORTS, 
viewName = PersonKeys.VIEW_REPORT_LEGAL_PERSON,
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class LegalPersonReportMV extends TModelView<LegalPersonReportModel>{
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
		panes={
			@TTitledPane(text=TUsualKey.FILTERS, node=@TNode(id="filtro",parse = true), 
				expanded=true, layoutType=TLayoutType.HBOX,
				fields={"title", "orderBy"}),
			@TTitledPane(text=TUsualKey.RESULT, node=@TNode(id="resultado",parse = true),
				fields={"result"})})	
	private SimpleStringProperty displayProperty;
	
	@TVBox(	pane=@TPane(children={"name", "otherName", "interval" }), spacing=10, fillWidth=true,
			vgrow=@TVGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
					@TPriority(field="otherName", priority=Priority.ALWAYS),
					@TPriority(field="interval", priority=Priority.ALWAYS)}))
	private SimpleStringProperty title;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120,
		node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.TRADE_NAME)
	@TTextField(maxLength=60)
	@THBox(	pane=@TPane(children={"otherName", "type",}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="otherName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty otherName;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = ILegalTypeController.JNDI_NAME, 
	optionModelViewClass=LegalTypeMV.class,
	entityClass=LegalType.class))
	private SimpleObjectProperty<LegalType> type;
	
	@THBox(pane=@TPane(children={"startActivities", "endActivities"}), spacing=10, fillHeight=true,
		hgrow=@THGrow(priority={@TPriority(field="startActivities", priority=Priority.ALWAYS), 
			@TPriority(field="endActivities", priority=Priority.ALWAYS)}))		
	private SimpleStringProperty interval;
	
	@TFieldSet(fields = { "startActivities", "startActivitiesEnd" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = TUsualKey.START_ACTIVITIES)
	@TLabel(text=TUsualKey.BEGIN)
	@TDatePickerField
	private SimpleObjectProperty<Date> startActivities;
	
	@TLabel(text=TUsualKey.END)
	@TDatePickerField
	private SimpleObjectProperty<Date> startActivitiesEnd;

	@TFieldSet(fields = { "endActivities", "endActivitiesEnd" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = TUsualKey.END_ACTIVITIES)
	@TLabel(text=TUsualKey.BEGIN)
	@TDatePickerField
	private SimpleObjectProperty<Date> endActivities;

	@TLabel(text=TUsualKey.END)
	@TDatePickerField
	private SimpleObjectProperty<Date> endActivitiesEnd;
	
	@TLabel(text=TFxKey.SORT_BY)
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend =TUsualKey.RESULT_ORDER)
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=TUsualKey.NAME, userData="e.name"),  
					@TRadioButton(text=TUsualKey.TRADE_NAME, userData="e.otherName"), 
					@TRadioButton(text=TUsualKey.TYPE, userData="t.name")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text=TFxKey.SORT_TYPE)
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=TFxKey.SORT_BY_ASC, userData="asc"), 
					@TRadioButton(text=TFxKey.SORT_BY_DESC, userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(editable=true, rowFactory=TReportRowFactoryCallBackBuilder.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
				@TTableColumn(cellValue="name", text = TUsualKey.NAME, prefWidth=20, resizable=true), 
				@TTableColumn(cellValue="otherName", text = TUsualKey.TRADE_NAME, resizable=true), 
				@TTableColumn(cellValue="type", text = TUsualKey.TYPE, resizable=true)
			})
	@TModelViewType(modelClass=LegalPersonItemModel.class, modelViewClass=LegalPersonItemMV.class)
	private ITObservableList<LegalPersonItemMV> result;
	
	public LegalPersonReportMV(LegalPersonReportModel entidade) {
		super(entidade);
	}

	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
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

	public ITObservableList<LegalPersonItemMV> getResult() {
		return result;
	}

	public void setResult(ITObservableList<LegalPersonItemMV> result) {
		this.result = result;
	}

	public SimpleStringProperty getOtherName() {
		return otherName;
	}

	public void setOtherName(SimpleStringProperty otherName) {
		this.otherName = otherName;
	}

	public SimpleObjectProperty<LegalType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<LegalType> type) {
		this.type = type;
	}

	public SimpleStringProperty getInterval() {
		return interval;
	}

	public void setInterval(SimpleStringProperty interval) {
		this.interval = interval;
	}

	public SimpleObjectProperty<Date> getStartActivities() {
		return startActivities;
	}

	public void setStartActivities(SimpleObjectProperty<Date> startActivities) {
		this.startActivities = startActivities;
	}

	public SimpleObjectProperty<Date> getStartActivitiesEnd() {
		return startActivitiesEnd;
	}

	public void setStartActivitiesEnd(SimpleObjectProperty<Date> startActivitiesEnd) {
		this.startActivitiesEnd = startActivitiesEnd;
	}

	public SimpleObjectProperty<Date> getEndActivities() {
		return endActivities;
	}

	public void setEndActivities(SimpleObjectProperty<Date> endActivities) {
		this.endActivities = endActivities;
	}

	public SimpleObjectProperty<Date> getEndActivitiesEnd() {
		return endActivitiesEnd;
	}

	public void setEndActivitiesEnd(SimpleObjectProperty<Date> endActivitiesEnd) {
		this.endActivitiesEnd = endActivitiesEnd;
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

}
