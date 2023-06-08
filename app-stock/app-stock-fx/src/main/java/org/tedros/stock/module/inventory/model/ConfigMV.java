/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.trigger.FilterCostCenterTrigger;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IStockConfigController;
import org.tedros.stock.entity.StockConfig;
import org.tedros.stock.entity.StockConfigItem;
import org.tedros.stock.module.inventory.builder.CostCenterValBuilder;
import org.tedros.stock.module.inventory.builder.LegalPersonValBuilder;
import org.tedros.stock.module.inventory.setting.ResponsableSetting;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(ResponsableSetting.class)
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
	@THBox(	pane=@TPane(children={"legalPerson", "costCenter", "responsable"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="costCenter", priority=Priority.NEVER), 
			@TPriority(field="legalPerson", priority=Priority.NEVER),
			@TPriority(field="responsable", priority=Priority.NEVER)}))
	private SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.COST_CENTER)
	@TComboBoxField(required=true)
	private SimpleObjectProperty<CostCenter> costCenter;

	@TLabel(text=TUsualKey.RESPONSABLE)
	@TAutoCompleteEntity( 
		showMaxItems=30, prefColumnCount=400,
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
	@TDetailListField(required=true, 
	entityModelViewClass = ConfigItemMV.class, entityClass = StockConfigItem.class)
	@TModelViewType(modelClass=StockConfigItem.class, modelViewClass=ConfigItemMV.class)
	private ITObservableList<ConfigItemMV> items;
	
	public ConfigMV(StockConfig entity) {
		super(entity);
		super.formatToString("%s [%s]", legalPerson, costCenter);
	}


}
