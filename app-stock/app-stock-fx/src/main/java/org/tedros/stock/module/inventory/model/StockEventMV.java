/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.Employee;
import org.tedros.stock.STCKKey;
import org.tedros.stock.ejb.controller.ICostCenterController;
import org.tedros.stock.entity.CostCenter;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.entity.StockItem;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class StockEventMV<E extends StockEvent> extends TEntityModelView<E> {


	@TLabel(text=STCKKey.COST_CENTER)
	@TAutoCompleteEntity(required=true,
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = CostCenter.class, fields = "name", 
	service = ICostCenterController.JNDI_NAME))
	protected SimpleObjectProperty<CostCenter> costCenter;
	
	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(required=true, 
	dateFormat=DateTimeFormatBuilder.class)
	protected SimpleObjectProperty<Date> date;
	
	@TLabel(text=STCKKey.RESPONSABLE)
	@TAutoCompleteEntity(
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = Employee.class, fields = {"name","lastName"}, 
	service = IPersonController.JNDI_NAME))
	protected SimpleObjectProperty<Employee> responsable;

	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(required=true, region=@TRegion(maxHeight=500, parse = false),
	entityModelViewClass = EventItemMV.class, entityClass = StockItem.class)
	@TModelViewType(modelClass=StockItem.class, modelViewClass=EventItemMV.class)
	protected ITObservableList<EventItemMV> items;

	@TLabel(text=TUsualKey.OBSERVATION)
	@TTextAreaField(wrapText=true)
	protected SimpleStringProperty observation;
	
	/**
	 * @param entity
	 */
	public StockEventMV(E entity) {
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

	public SimpleObjectProperty<Employee> getResponsable() {
		return responsable;
	}

	public void setResponsable(SimpleObjectProperty<Employee> responsable) {
		this.responsable = responsable;
	}

	public ITObservableList<EventItemMV> getItems() {
		return items;
	}

	public void setItems(ITObservableList<EventItemMV> items) {
		this.items = items;
	}

	public SimpleStringProperty getObservation() {
		return observation;
	}

	public void setObservation(SimpleStringProperty observation) {
		this.observation = observation;
	}


}
