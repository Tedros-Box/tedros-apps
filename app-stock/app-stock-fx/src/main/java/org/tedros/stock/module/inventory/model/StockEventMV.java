/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.trigger.FilterCostCenterTrigger;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.entity.StockItem;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class StockEventMV<E extends StockEvent> extends TEntityModelView<E> {


	@TLabel(text=TUsualKey.LEGAL_PERSON)
	@TAutoCompleteEntity(required=true,
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = LegalPerson.class, 
	fields = {"name","otherName"}, 
	service = IPersonController.JNDI_NAME))
	@TTrigger(triggerClass = FilterCostCenterTrigger.class, 
	targetFieldName="costCenter", runAfterFormBuild=true)
	protected SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.COST_CENTER)
	@TComboBoxField()
	protected SimpleObjectProperty<CostCenter> costCenter;
	
	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(required=true, 
	dateFormat=DateTimeFormatBuilder.class)
	protected SimpleObjectProperty<Date> date;
	
	@TLabel(text=TUsualKey.RESPONSABLE)
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

}
