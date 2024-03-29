package org.tedros.stock.module.report.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.control.TVRadioGroup;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TReportProcess;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.builder.TRowFactoryWithOpenAndRemoveAction;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;
import org.tedros.fx.presenter.report.decorator.TDataSetReportDecorator;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.module.report.action.SearchAction;
import org.tedros.person.trigger.FilterCostCenterTrigger;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;
import org.tedros.stock.model.Inventory;
import org.tedros.stock.model.InventoryReportModel;
import org.tedros.stock.module.report.process.InventoryReportProcess;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Priority;


@TForm(header = "", showBreadcrumBar=false, editCssId="")
@TReportProcess(type=InventoryReportProcess.class, model = InventoryReportModel.class)
@TPresenter(type = TDynaPresenter.class,
	behavior = @TBehavior(type = TDataSetReportBehavior.class, 
		action=SearchAction.class), 
	decorator = @TDecorator(type = TDataSetReportDecorator.class, 
		viewTitle=STCKKey.VIEW_INVENTORY_REPORT))
@TSecurity(	id=DomainApp.INVENTORY_REPORT_FORM_ID, 
appName = STCKKey.APP_STOCK, 
moduleName = STCKKey.MODULE_INVENTORY, 
viewName = STCKKey.VIEW_INVENTORY_REPORT,
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class InventoryReportMV extends TModelView<InventoryReportModel>{
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
		panes={
			@TTitledPane(text=TUsualKey.FILTERS, node=@TNode(id="filtro",parse = true), 
				expanded=true, layoutType=TLayoutType.HBOX,
				fields={"legalPerson", "orderBy"}),
			@TTitledPane(text=TUsualKey.RESULT, node=@TNode(id="resultado",parse = true),
				fields={"result"})})	
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.LEGAL_PERSON)
	@TAutoCompleteEntity(
	startSearchAt=3, showMaxItems=30,
	service = IPersonController.JNDI_NAME,
	query = @TQuery(entity = LegalPerson.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "otherName", 
			operator=TCompareOp.LIKE)}))
	@TTrigger(type = FilterCostCenterTrigger.class, 
	targetFieldName="costCenter", runAfterFormBuild=true)
	@THBox(	pane=@TPane(children={"legalPerson", "costCenter", "date", "product"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="product", priority=Priority.NEVER), 
			@TPriority(field="costCenter", priority=Priority.NEVER), 
			@TPriority(field="date", priority=Priority.NEVER),
			@TPriority(field="legalPerson", priority=Priority.NEVER)}))
	private SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.COST_CENTER)
	@TComboBoxField()
	private SimpleObjectProperty<CostCenter> costCenter;
	
	@TLabel(text=TUsualKey.UNTIL_DATE)
	@TDatePickerField(
	dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> date;
	
	@TLabel(text=TUsualKey.PRODUCT)
	@TAutoCompleteEntity(showMaxItems=30,
	service = IProductController.JNDI_NAME,
	query = @TQuery(entity = Product.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "code", 
			operator=TCompareOp.LIKE)}))
	private SimpleObjectProperty<Product> product;
	
	@TLabel(text=TFxKey.SORT_BY)
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend =TUsualKey.RESULT_ORDER)
	@TVRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radio = {@TRadio(text=TUsualKey.NAME, userData="p.name"),  
					@TRadio(text=TUsualKey.CODE, userData="p.code")
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
			rowFactory=TRowFactoryWithOpenAndRemoveAction.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
				@TTableColumn(cellValue="legalPerson", text = TUsualKey.LEGAL_PERSON, prefWidth=20, resizable=true), 
				@TTableColumn(cellValue="costCenter", text = TUsualKey.COST_CENTER, prefWidth=20, resizable=true), 
				@TTableColumn(cellValue="code", text = TUsualKey.CODE, prefWidth=20, resizable=true), 
				@TTableColumn(cellValue="name", text = TUsualKey.NAME, resizable=true), 
				@TTableColumn(cellValue="amount", text = TUsualKey.AMOUNT, resizable=true)
			})
	@TGenericType(model=Inventory.class, modelView=InventoryTV.class)
	private ITObservableList<InventoryTV> result;
	
	public InventoryReportMV(InventoryReportModel entidade) {
		super(entidade);
	}


}
