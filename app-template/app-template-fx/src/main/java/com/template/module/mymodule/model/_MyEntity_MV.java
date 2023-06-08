/**
 * 
 */
package com.template.module.mymodule.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
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
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;

import com.template.TMP_Key;
import com.template.domain.DomainApp;
import com.template.ejb.controller.I_MyEntity_Controller;
import com.template.entity._MyEntity_;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author myname
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = I_MyEntity_Controller.JNDI_NAME, model=_MyEntity_.class)
@TListViewPresenter(
	page=@TPage(serviceName = I_MyEntity_Controller.JNDI_NAME,
		query = @TQuery(entity=_MyEntity_.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
		),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=TMP_Key.MY_APP_MY_VIEW,
		buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.MY_ENTITY__FORM_ID, appName = TMP_Key.APP_MY_APP,
	moduleName = TMP_Key.MODULE_MY_APP, viewName = TMP_Key.MY_APP_MY_VIEW,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class _MyEntity_MV extends TEntityModelView<_MyEntity_> {

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
}
