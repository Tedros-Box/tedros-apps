package org.tedros.stock.module.report.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.control.TVerticalRadioGroup;
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
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.builder.TReportRowFactoryCallBackBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;
import org.tedros.fx.presenter.report.decorator.TDataSetReportDecorator;
import org.tedros.person.module.report.action.SearchAction;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.ICostCenterController;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.CostCenter;
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


@TForm(name = "", showBreadcrumBar=false, editCssId="")
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
				fields={"costCenter", "orderBy"}),
			@TTitledPane(text=TUsualKey.RESULT, node=@TNode(id="resultado",parse = true),
				fields={"result"})})	
	private SimpleLongProperty id;
	
	@TLabel(text=STCKKey.COST_CENTER)
	@TAutoCompleteEntity(
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = CostCenter.class, fields = "name", 
	service = ICostCenterController.JNDI_NAME))
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"costCenter", "date", "product"}), 
	hgrow=@THGrow(priority={@TPriority(field="product", priority=Priority.NEVER), 
		@TPriority(field="costCenter", priority=Priority.NEVER), 
		@TPriority(field="date", priority=Priority.NEVER)}))
	private SimpleObjectProperty<CostCenter> costCenter;
	
	@TLabel(text=STCKKey.UNTIL_DATE)
	@TDatePickerField(
	dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> date;
	
	@TLabel(text=STCKKey.PRODUCT)
	@TAutoCompleteEntity(
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = Product.class, fields = {"code", "name"}, 
	service = IProductController.JNDI_NAME))
	private SimpleObjectProperty<Product> product;
	
	@TLabel(text=TFxKey.SORT_BY)
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend =TUsualKey.RESULT_ORDER)
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=TUsualKey.NAME, userData="p.name"),  
					@TRadioButton(text=TUsualKey.CODE, userData="p.code")
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
				@TTableColumn(cellValue="code", text = TUsualKey.CODE, prefWidth=20, resizable=true), 
				@TTableColumn(cellValue="name", text = TUsualKey.NAME, resizable=true), 
				@TTableColumn(cellValue="amount", text = TUsualKey.AMOUNT, resizable=true)
			})
	@TModelViewType(modelClass=Inventory.class, modelViewClass=InventoryTV.class)
	private ITObservableList<InventoryTV> result;
	
	public InventoryReportMV(InventoryReportModel entidade) {
		super(entidade);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleObjectProperty<CostCenter> getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(SimpleObjectProperty<CostCenter> costCenter) {
		this.costCenter = costCenter;
	}

	public SimpleObjectProperty<Date> getDate() {
		return date;
	}

	public void setDate(SimpleObjectProperty<Date> date) {
		this.date = date;
	}

	public SimpleObjectProperty<Product> getProduct() {
		return product;
	}

	public void setProduct(SimpleObjectProperty<Product> product) {
		this.product = product;
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

	public ITObservableList<InventoryTV> getResult() {
		return result;
	}

	public void setResult(ITObservableList<InventoryTV> result) {
		this.result = result;
	}

}
