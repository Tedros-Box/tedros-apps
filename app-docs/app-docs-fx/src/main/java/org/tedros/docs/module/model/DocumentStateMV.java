/**
 * 
 */
package org.tedros.docs.module.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.docs.domain.DomainApp;
import org.tedros.docs.ejb.controller.IDocumentStateController;
import org.tedros.docs.model.DocumentState;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TFormReaderHtml;
import org.tedros.fx.annotation.reader.TReaderHtml;
import org.tedros.fx.annotation.reader.TTextReaderHtml;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.THtmlConstant;
import org.tedros.fx.domain.TStyleParameter;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.docs.state}", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IDocumentStateController.JNDI_NAME, model=DocumentState.class)
@TListViewPresenter(presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.docs.state}"),
	behavior=@TBehavior(runNewActionAfterSave=true)))
@TSecurity(id=DomainApp.DOCUMENT_STATE_FORM_ID, 
	appName = "#{app.docs}", moduleName = "#{module.docs}", viewName = "#{view.docs.state}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DocumentStateMV extends TEntityModelView<DocumentState> {
	
	@TTextReaderHtml(text="#{label.docs.state}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", parse = true))
	@TText(text="#{header.docs.state}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text="#{label.description}")
	@TTextAreaField(maxLength=250)
	private SimpleStringProperty description;
	
	public DocumentStateMV(DocumentState entity) {
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

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

}
