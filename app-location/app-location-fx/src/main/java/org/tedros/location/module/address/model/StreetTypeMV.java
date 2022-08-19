/**
 * 
 */
package org.tedros.location.module.address.model;

import static org.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static org.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static org.tedros.core.annotation.security.TAuthorizationType.NEW;
import static org.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.ejb.controller.IStreetTypeController;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.location.LocatKey;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.model.StreetType;

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
	public SimpleStringProperty toStringProperty() {
		return name;
	}

}
