/**
 * 
 */
package org.tedros.services.module.service.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.model.FindPlaceMV;
import org.tedros.extension.model.Place;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TSingleSelectionModal;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
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
import org.tedros.fx.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;
import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.ejb.controller.IServiceLocationController;
import org.tedros.services.model.ServiceLocation;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IServiceLocationController.JNDI_NAME, model=ServiceLocation.class)
@TListViewPresenter(
	page=@TPage(serviceName = IServiceLocationController.JNDI_NAME,
		query = @TQuery(entity=ServiceLocation.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_SERVICE_LOCATION,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.SERVICE_LOCATION_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_SERVICES, viewName = ServKey.VIEW_SERVICE_LOCATION,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ServiceLocationMV extends TEntityModelView<ServiceLocation> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	@TLabel(text=LocatKey.VIEW_PLACE)
	@TSingleSelectionModal(height=50,
		model = Place.class, modelView = FindPlaceMV.class)
	private SimpleObjectProperty<Place> place;
	
	public ServiceLocationMV(ServiceLocation entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
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

	public SimpleObjectProperty<Place> getPlace() {
		return place;
	}

	public void setPlace(SimpleObjectProperty<Place> place) {
		this.place = place;
	}
}
