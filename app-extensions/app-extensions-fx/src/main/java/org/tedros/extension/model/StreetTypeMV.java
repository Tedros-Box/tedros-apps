/**
 * 
 */
package org.tedros.extension.model;

import static org.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static org.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static org.tedros.core.annotation.security.TAuthorizationType.NEW;
import static org.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.fx.annotation.assistant.TAiAssistant;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = LocatKey.FORM_STREET, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IExtensionDomainController.JNDI_NAME, model=StreetType.class)
@TListViewPresenter(
	aiAssistant=@TAiAssistant(jsonModel = StreetTypeJsonModel.class, show=true,
		modelViewClass = StreetTypeMV.class),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=LocatKey.VIEW_STREET_TYPE),
		behavior = @TBehavior(runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.STREET_TYPE_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_STREET_TYPE,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class StreetTypeMV extends ExtensionDomainMV<StreetType> {

	@TFieldBox(alignment=Pos.CENTER_LEFT, 
			node=@TNode(id=TFieldBox.TITLE, parse = true))
	@TText(text=LocatKey.HEADER_STREET, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	public StreetTypeMV(StreetType entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

}
