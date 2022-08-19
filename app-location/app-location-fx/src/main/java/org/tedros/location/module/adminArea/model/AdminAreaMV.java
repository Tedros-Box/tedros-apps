/**
 * 
 */
package com.tedros.location.module.adminArea.model;

import static com.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static com.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static com.tedros.core.annotation.security.TAuthorizationType.NEW;
import static com.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static com.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.controller.IAdminAreaController;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.LocatKey;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.AdminArea;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = LocatKey.FORM_KEEP_UPDATE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IAdminAreaController.JNDI_NAME, model=AdminArea.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = AdminArea.class, serviceName = IAdminAreaController.JNDI_NAME,
			show=true, showSearchField=true, searchFieldName="name", 
			orderBy = {	@TOption(text = TUsualKey.COUNTRY_CODE, value = "countryIso2Code"), 
						@TOption(text = TUsualKey.NAME, value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=LocatKey.VIEW_ADMIN_AREA, buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=AdminAreaImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.ADMIN_AREA_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_ADMIN_AREA,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class AdminAreaMV extends TEntityModelView<AdminArea> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty display;
	
	@TLabel(text=TUsualKey.COUNTRY_CODE+" (ISO2)")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"countryIso2Code", "name", "iso2Code"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="countryIso2Code", priority=Priority.NEVER), 
			@TPriority(field="iso2Code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty countryIso2Code;
			
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
			
	@TLabel(text=TUsualKey.CAPITAL)
	@TTextField(maxLength=120, required = false)
	private SimpleStringProperty iso2Code;
			
	
	public AdminAreaMV(AdminArea e) {
		super(e);
		this.formatFieldsToDisplay("[%s] %s", this.countryIso2Code, this.name);
	}
	
	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return display;
	}

	public SimpleStringProperty getCountryIso2Code() {
		return countryIso2Code;
	}

	public void setCountryIso2Code(SimpleStringProperty countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getIso2Code() {
		return iso2Code;
	}

	public void setIso2Code(SimpleStringProperty iso2Code) {
		this.iso2Code = iso2Code;
	}

	public SimpleStringProperty getDisplay() {
		return display;
	}

	public void setDisplay(SimpleStringProperty display) {
		this.display = display;
	}

}
