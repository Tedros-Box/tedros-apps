/**
 * 
 */
package org.tedros.services.module.service.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TMultipleSelectionModal;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;
import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.ejb.controller.IServiceController;
import org.tedros.services.ejb.controller.IServiceTypeController;
import org.tedros.services.model.Plan;
import org.tedros.services.model.Service;
import org.tedros.services.model.ServiceType;
import org.tedros.services.module.plan.model.FindPlanMV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IServiceController.JNDI_NAME, model=Service.class)
@TListViewPresenter(
	page=@TPage(serviceName = IServiceController.JNDI_NAME,
		query = @TQuery(entity=Service.class, 
			condition= {@TCondition(field = "code", operator=TCompareOp.EQUAL, label=TUsualKey.REF_CODE),
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name"),
				@TOrder(label = TUsualKey.REF_CODE , field = "code")}
		),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_SERVICE,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.SERVICE_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_SERVICES, viewName = ServKey.VIEW_SERVICE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
		TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ServiceMV extends TEntityModelView<Service> {

	@TTabPane(tabs = { 
			@TTab(closable=false, text = TUsualKey.MAIN_DATA, scroll=false,
					fields={"code","description", "plans"}),  
			@TTab(closable=false, text = TUsualKey.OBSERVATION, fields={"observation"})
		})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.REF_CODE)
	@TTextField(maxLength=15)
	@THBox(	pane=@TPane(children={"code","type","name"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.NEVER), 
			@TPriority(field="type", priority=Priority.NEVER)}))
	private SimpleStringProperty code;

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=250, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(required=true,
	process=@TProcess(service = IServiceTypeController.JNDI_NAME, 
	modelView=ServiceTypeMV.class, query=@TQuery(entity=ServiceType.class)))
	private SimpleObjectProperty<ServiceType> type;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	@TLabel(text=TUsualKey.PLANS)
	@TMultipleSelectionModal(height=100,
	model = Plan.class, modelView = FindPlanMV.class)
	@TGenericType(model=Plan.class, modelView=FindPlanMV.class)
	public ITObservableList<Plan> plans;
	
	@TTextAreaField(maxLength=1024, wrapText=true)
	private SimpleStringProperty observation;
	
	public ServiceMV(Service entity) {
		super(entity);
		super.formatToString("%s %s", code, name);
	}

}
