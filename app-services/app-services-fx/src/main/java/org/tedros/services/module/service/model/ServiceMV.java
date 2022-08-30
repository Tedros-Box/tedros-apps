/**
 * 
 */
package org.tedros.services.module.service.model;

import org.tedros.docs.export.ModalDocumentMV;
import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.ejb.controller.IServiceController;
import org.tedros.services.ejb.controller.IServiceTypeController;
import org.tedros.services.model.Plan;
import org.tedros.services.model.Service;
import org.tedros.services.model.ServiceType;
import org.tedros.services.module.plan.model.FindPlanMV;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TMultipleSelectionModal;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TDetailForm;
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
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IServiceController.JNDI_NAME, model=Service.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Service.class, serviceName = IServiceController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name"),
				@TOption(text = TUsualKey.REF_CODE , value = "code")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_SERVICE,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.SERVICE_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_SERVICES, viewName = ServKey.VIEW_SERVICE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ServiceMV extends TEntityModelView<Service> {

	@TTabPane(tabs = { 
			@TTab(closable=false, text = TUsualKey.MAIN_DATA, scroll=false,
				content = @TContent(detailForm=@TDetailForm(fields={"code","description", "plans"}))),  
			@TTab(closable=false, text = TUsualKey.OBSERVATION, 
				content = @TContent(detailForm=@TDetailForm(fields={"observation"})))
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
	optionsList=@TOptionsList(serviceName = IServiceTypeController.JNDI_NAME, 
	optionModelViewClass=ServiceTypeMV.class,
	entityClass=ServiceType.class))
	private SimpleObjectProperty<ServiceType> type;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	@TLabel(text=TUsualKey.PLANS)
	@TMultipleSelectionModal(height=100,
	modelClass = Plan.class, modelViewClass = FindPlanMV.class)
	@TModelViewType(modelClass=Plan.class, modelViewClass=ModalDocumentMV.class)
	public ITObservableList<Plan> plans;
	
	@TTextAreaField(maxLength=1024, wrapText=true)
	private SimpleStringProperty observation;
	
	public ServiceMV(Service entity) {
		super(entity);
		super.formatToString("%s %s", code, name);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleStringProperty getCode() {
		return code;
	}

	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	public SimpleStringProperty getObservation() {
		return observation;
	}

	public void setObservation(SimpleStringProperty observation) {
		this.observation = observation;
	}

	public ITObservableList<Plan> getPlans() {
		return plans;
	}

	public void setPlans(ITObservableList<Plan> plans) {
		this.plans = plans;
	}

	public SimpleObjectProperty<ServiceType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<ServiceType> type) {
		this.type = type;
	}
}
