/**
 * 
 */
package org.tedros.stock.module.config.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.ICostCenterController;
import org.tedros.stock.ejb.controller.IStockConfigController;
import org.tedros.stock.entity.CostCenter;
import org.tedros.stock.entity.StockConfig;
import org.tedros.stock.entity.StockConfigItem;
import org.tedros.stock.module.costcenter.table.CostCenterTV;

import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IStockConfigController.JNDI_NAME, model=StockConfig.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = StockConfig.class, serviceName = IStockConfigController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=STCKKey.VIEW_STOCK_CONFIG,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))/*
@TSecurity(id=DomainApp.STOCK_CONFIG_FORM_ID, appName = STCKKey.APP_STOCK,
	moduleName = STCKKey.MODULE_PRODUCTS, viewName = STCKKey.VIEW_STOCK_CONFIG,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})*/
public class ConfigMV extends TEntityModelView<StockConfig> {

	@TLabel(text=STCKKey.VIEW_COST_CENTER)
	@TAutoCompleteEntity(modelViewType=CostCenterTV.class, 
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = CostCenter.class, fields = "name", 
	service = ICostCenterController.JNDI_NAME))
	private SimpleObjectProperty<CostCenter> costCenter;

	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> date;
	
	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(entityModelViewClass = ItemMV.class, entityClass = StockConfigItem.class)
	@TModelViewType(modelClass=StockConfigItem.class, modelViewClass=ItemMV.class)
	private ITObservableList<ItemMV> items;
	
	public ConfigMV(StockConfig entity) {
		super(entity);
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

	public ITObservableList<ItemMV> getItems() {
		return items;
	}

	public void setItems(ITObservableList<ItemMV> items) {
		this.items = items;
	}

}
