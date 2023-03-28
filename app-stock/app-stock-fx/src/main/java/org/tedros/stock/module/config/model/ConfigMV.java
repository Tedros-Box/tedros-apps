/**
 * 
 */
package org.tedros.stock.module.config.model;

import java.util.Date;

import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.Person;
import org.tedros.stock.STCKKey;
import org.tedros.stock.ejb.controller.ICostCenterController;
import org.tedros.stock.entity.CostCenter;
import org.tedros.stock.entity.StockConfig;
import org.tedros.stock.module.costcenter.table.CostCenterTV;

import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Davis Gordon
 *
 */
public class ConfigMV extends TEntityModelView<StockConfig> {

	@TLabel(text=STCKKey.VIEW_COST_CENTER)
	@TAutoCompleteEntity(modelViewType=CostCenterTV.class, 
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = CostCenter.class, fields = "name", 
	service = ICostCenterController.JNDI_NAME))
	private SimpleObjectProperty<CostCenter> costCenter;

	private SimpleObjectProperty<Date> date;
	
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
