/**
 * 
 */
package org.tedros.stock.module.inventory.model;

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
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
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

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IStockConfigController.JNDI_NAME, model=StockConfig.class)
@TListViewPresenter(
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=STCKKey.VIEW_STOCK_CONFIG,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.STOCK_CONFIG_FORM_ID, appName = STCKKey.APP_STOCK,
	moduleName = STCKKey.MODULE_INVENTORY, viewName = STCKKey.VIEW_STOCK_CONFIG,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ConfigMV extends TEntityModelView<StockConfig> {

	@TLabel(text=STCKKey.COST_CENTER)
	@TAutoCompleteEntity(
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = CostCenter.class, fields = "name", 
	service = ICostCenterController.JNDI_NAME))
	@THBox(	pane=@TPane(children={"costCenter", "date"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="costCenter", priority=Priority.NEVER), 
			@TPriority(field="date", priority=Priority.NEVER)}))
	private SimpleObjectProperty<CostCenter> costCenter;

	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> date;
	
	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(entityModelViewClass = ConfigItemMV.class, entityClass = StockConfigItem.class)
	@TModelViewType(modelClass=StockConfigItem.class, modelViewClass=ConfigItemMV.class)
	private ITObservableList<ConfigItemMV> items;
	
	public ConfigMV(StockConfig entity) {
		super(entity);
		super.formatToString("%s", costCenter);
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

	public ITObservableList<ConfigItemMV> getItems() {
		return items;
	}

	public void setItems(ITObservableList<ConfigItemMV> items) {
		this.items = items;
	}

}
