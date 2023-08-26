/**
 * 
 */
package org.tedros.samples.module.order.model;

import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.chart.TAxis;
import org.tedros.fx.annotation.chart.TBarChart;
import org.tedros.fx.annotation.chart.TXYChart;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TJoin;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TAxisType;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.trigger.FilterCostCenterTrigger;
import org.tedros.sample.ejb.controller.IGenericDomainController;
import org.tedros.sample.ejb.controller.IOrderController;
import org.tedros.sample.entity.Order;
import org.tedros.sample.entity.OrderItem;
import org.tedros.sample.entity.OrderStatus;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.order.builder.CustomerToStringBuilder;
import org.tedros.samples.module.order.builder.DocTypeOtherBuilder;
import org.tedros.samples.module.order.builder.OrderParamBuilder;
import org.tedros.samples.module.order.trigger.ChartBuildTrigger;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TJoinType;
import org.tedros.server.query.TLogicOp;
import org.tedros.stock.module.inventory.builder.CostCenterValBuilder;
import org.tedros.stock.module.inventory.builder.LegalPersonValBuilder;

import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Davis Gordon
 *
 */
//@TSetting(OrderSetting.class)
@TForm(showBreadcrumBar=false, scroll=true)
@TPresenter(model=Order.class,
		decorator = @TDecorator(type=TViewDecorator.class, 
		viewTitle=SmplsKey.VIEW_ORDERS + " Chart"),
		behavior=@TBehavior(type=TViewBehavior.class))
/*@TSecurity(id=DomainApp.ORDER_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = SmplsKey.MODULE_ORDERS, viewName = SmplsKey.VIEW_ORDERS,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})*/
public class OrderChartMV extends TEntityModelView<Order> {
	
	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	@TFlowPane(hgap=15, vgap=15,
	pane=@TPane(children={"begin", "end","customer", "status", 
		"legalPerson", "costCenter", "seller"}))
	@TTrigger(type = ChartBuildTrigger.class, targetFieldName="chart")
	private SimpleObjectProperty<Date> begin;
	
	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	@TTrigger(type = ChartBuildTrigger.class, targetFieldName="chart")
	private SimpleObjectProperty<Date> end;

	@TLabel(text=TUsualKey.CUSTOMER)
	@TAutoCompleteEntity(
			showMaxItems=30,
			converter=CustomerToStringBuilder.class,
			service = IPersonController.JNDI_NAME,
			query = @TQuery(entity = NaturalPerson.class, 
				join = {
						@TJoin(type=TJoinType.LEFT, field = "documents", joinAlias = "doc"),
						@TJoin(type=TJoinType.LEFT, alias="doc", field = "type", joinAlias = "dtp")
				},
				condition = {
					@TCondition(field = "name", operator=TCompareOp.LIKE),
					@TCondition(logicOp=TLogicOp.OR, field = "lastName", 
					operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.OR, alias="doc", field = "value", 
					operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.AND, alias="dtp", field = "docType", 
					operator=TCompareOp.NOT_EQ,
					valueBuilder=DocTypeOtherBuilder.class, prompted=false)}))
	@TTrigger(type = ChartBuildTrigger.class, targetFieldName="chart")
	private SimpleObjectProperty<NaturalPerson> customer;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(process=@TProcess(
		service = IGenericDomainController.JNDI_NAME, 
		query=@TQuery(entity=OrderStatus.class)))
	@TTrigger(type = ChartBuildTrigger.class, targetFieldName="chart")
	private SimpleObjectProperty<OrderStatus> status;

	@TLabel(text=TUsualKey.LEGAL_PERSON)
	@TAutoCompleteEntity(startSearchAt=3, showMaxItems=30,
	service = IPersonController.JNDI_NAME,
	query = @TQuery(entity = LegalPerson.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "otherName", 
			operator=TCompareOp.LIKE)}))
	@TTrigger(type = FilterCostCenterTrigger.class, 
	targetFieldName="costCenter", runAfterFormBuild=true)
	private SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.COST_CENTER)
	@TComboBoxField
	@TTrigger(type = ChartBuildTrigger.class, targetFieldName="chart")
	private SimpleObjectProperty<CostCenter> costCenter;
	
	@TLabel(text=TUsualKey.EMPLOYEE)
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
	@TTrigger(type = ChartBuildTrigger.class, targetFieldName="chart")
	private SimpleObjectProperty<Employee> seller;

	@TDetailField(model = ChartModel.class, modelView = ChartMV.class, showButtons=true)
	private SimpleObjectProperty<ChartMV> chart;
	
	public OrderChartMV(Order entity) {
		super(entity);
		super.registerProperty("begin", begin);
		super.registerProperty("end", end);
		super.registerProperty("chart", chart);
		
		chart.setValue(new ChartMV(new ChartModel()));
		
	}
	
}
