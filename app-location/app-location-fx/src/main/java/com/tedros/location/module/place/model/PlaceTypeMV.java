/**
 * 
 */
package com.tedros.location.module.place.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.PlaceType;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.place.type}", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = "IPlaceTypeControllerRemote", model=PlaceType.class)
@TListViewPresenter(presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.place.type}"),
	behavior=@TBehavior(runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.PLACE_TYPE_FORM_ID, 
	appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.place.type}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PlaceTypeMV extends TEntityModelView<PlaceType> {

	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="#{label.place.type}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", parse = true))
	@TText(text="#{header.place.type}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=30, required = true, node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty name;
	
	public PlaceTypeMV(PlaceType entity) {
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
