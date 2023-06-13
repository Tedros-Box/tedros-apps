/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.trigger.FilterCostCenterTrigger;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.entity.StockItem;
import org.tedros.stock.module.inventory.builder.CostCenterValBuilder;
import org.tedros.stock.module.inventory.builder.LegalPersonValBuilder;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class StockEventMV<E extends StockEvent> extends TEntityModelView<E> {


	@TLabel(text=TUsualKey.LEGAL_PERSON)
	@TAutoCompleteEntity(required=true, 
	startSearchAt=3, showMaxItems=30,
	service = IPersonController.JNDI_NAME,
	query = @TQuery(entity = LegalPerson.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "otherName", 
			operator=TCompareOp.LIKE)}))
	@TTrigger(triggerClass = FilterCostCenterTrigger.class, 
	targetFieldName="costCenter", runAfterFormBuild=true)
	protected SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.COST_CENTER)
	@TComboBoxField(required=true)
	protected SimpleObjectProperty<CostCenter> costCenter;
	
	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(required=true, 
	dateFormat=DateTimeFormatBuilder.class)
	protected SimpleObjectProperty<Date> date;
	
	@TLabel(text=TUsualKey.RESPONSABLE)
	@TAutoCompleteEntity(
		startSearchAt=3, showMaxItems=30,
		service = IEmployeeController.JNDI_NAME,
		query = @TQuery(entity = Employee.class, 
			condition = {
				@TCondition(field = "name", operator=TCompareOp.LIKE),
				@TCondition(logicOp=TLogicOp.OR, field = "lastName", 
				operator=TCompareOp.LIKE), 
				@TCondition(logicOp=TLogicOp.AND, field = "legalPerson", 
				valueBuilder=LegalPersonValBuilder.class, prompted=false),
				@TCondition(logicOp=TLogicOp.AND, field = "costCenter", 
				valueBuilder=CostCenterValBuilder.class, prompted=false)}))
	protected SimpleObjectProperty<Employee> responsable;

	@TLabel(text=TUsualKey.PRODUCTS, show=false)
	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(required=true, region=@TRegion(maxHeight=500, parse = false),
	modelView = EventItemMV.class, entity = StockItem.class)
	@TGenericType(model=StockItem.class, modelView=EventItemMV.class)
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
