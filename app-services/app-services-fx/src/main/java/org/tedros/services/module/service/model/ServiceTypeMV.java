/**
 * 
 */
package com.tedros.services.module.service.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.services.ServKey;
import com.tedros.services.domain.DomainApp;
import com.tedros.services.ejb.controller.IServiceTypeController;
import com.tedros.services.model.ServiceType;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IServiceTypeController.JNDI_NAME, model=ServiceType.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = ServiceType.class, serviceName = IServiceTypeController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_SERVICE_TYPE,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.SERVICE_TYPE_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_SERVICES, viewName = ServKey.VIEW_SERVICE_TYPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ServiceTypeMV extends TEntityModelView<ServiceType> {

	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	public ServiceTypeMV(ServiceType entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
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
}
