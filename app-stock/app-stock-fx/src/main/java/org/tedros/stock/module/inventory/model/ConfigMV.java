/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
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
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.Employee;
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

	@TLabel(text=TUsualKey.COST_CENTER)
	@TAutoCompleteEntity(
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = CostCenter.class, fields = "name", 
	service = ICostCenterController.JNDI_NAME))
	@THBox(	pane=@TPane(children={"costCenter", "responsable"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="costCenter", priority=Priority.NEVER), 
			@TPriority(field="responsable", priority=Priority.NEVER)}))
	private SimpleObjectProperty<CostCenter> costCenter;

	@TLabel(text=TUsualKey.RESPONSABLE)
	@TAutoCompleteEntity(
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = Employee.class, fields = {"name","lastName"}, 
	service = IPersonController.JNDI_NAME))
	protected SimpleObjectProperty<Employee> responsable;
	
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

	public ITObservableList<ConfigItemMV> getItems() {
		return items;
	}

	public void setItems(ITObservableList<ConfigItemMV> items) {
		this.items = items;
	}

	public SimpleObjectProperty<Employee> getResponsable() {
		return responsable;
	}

	public void setResponsable(SimpleObjectProperty<Employee> responsable) {
		this.responsable = responsable;
	}

}
