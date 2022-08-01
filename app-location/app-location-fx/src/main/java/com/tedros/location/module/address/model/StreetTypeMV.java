/**
 * 
 */
package com.tedros.location.module.address.model;

import static com.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static com.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static com.tedros.core.annotation.security.TAuthorizationType.NEW;
import static com.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static com.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.controller.IStreetTypeController;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.LocatKey;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.StreetType;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = LocatKey.FORM_STREET, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IStreetTypeController.JNDI_NAME, model=StreetType.class)
@TListViewPresenter(presenter=@TPresenter(
	decorator = @TDecorator(viewTitle=LocatKey.VIEW_STREET_TYPE),
	behavior=@TBehavior(runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.STREET_TYPE_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_STREET_TYPE,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class StreetTypeMV extends TEntityModelView<StreetType> {

	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", parse = true))
	@TText(text=LocatKey.HEADER_STREET, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=30, required = true, node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty name;
	
	public StreetTypeMV(StreetType entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getHeader() {
		return header;
	}

	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}
