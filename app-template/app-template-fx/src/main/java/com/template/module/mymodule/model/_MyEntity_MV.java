/**
 * 
 */
package com.template.module.mymodule.model;

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
import com.template.TMP_Key;
import com.template.domain.DomainApp;
import com.template.ejb.controller.I_MyEntity_Controller;
import com.template.entity._MyEntity_;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author myname
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = I_MyEntity_Controller.JNDI_NAME, model=_MyEntity_.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = _MyEntity_.class, serviceName = I_MyEntity_Controller.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=TMP_Key.MY_APP_MY_VIEW,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.MY_ENTITY__FORM_ID, appName = TMP_Key.APP_MY_APP,
	moduleName = TMP_Key.MODULE_MY_APP, viewName = TMP_Key.MY_APP_MY_VIEW,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class _MyEntity_MV extends TEntityModelView<_MyEntity_> {

	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	public _MyEntity_MV(_MyEntity_ entity) {
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
