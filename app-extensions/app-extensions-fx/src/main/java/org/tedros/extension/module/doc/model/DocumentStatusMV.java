/**
 * 
 */
package org.tedros.extension.module.doc.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.ExtKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.extension.model.DocumentStatus;
import org.tedros.extension.model.ExtensionDomainMV;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.assistant.TAiAssistant;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.presenter.model.TFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = ExtKey.FORM_DOCS_STATE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IExtensionDomainController.JNDI_NAME, model=DocumentStatus.class)
@TListViewPresenter(
	aiAssistant=@TAiAssistant(jsonModel = DocumentStatusJsonModel.class, show=true,
		modelViewClass = DocumentStatusMV.class),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=ExtKey.VIEW_DOCS_STATE),
		behavior = @TBehavior(runNewActionAfterSave=true)))
@TSecurity(id=DomainApp.DOCUMENT_STATUS_FORM_ID, 
	appName = ExtKey.APP_EXTS, moduleName = ExtKey.MODULE_DOCS, viewName = ExtKey.VIEW_DOCS_STATE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DocumentStatusMV extends ExtensionDomainMV<DocumentStatus> {
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, 
		node=@TNode(id=TFieldBox.TITLE, parse = true))
	@TText(text=ExtKey.HEADER_DOCS_STATE, 
		textAlignment=TextAlignment.LEFT, 
		textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TLabel(text=TUsualKey.CODE)
	@TTextField(maxLength=20)
	@THBox(	pane=@TPane(children={"code","name"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={
					@TPriority(field="code", priority=Priority.NEVER),
					@TPriority(field="name", priority=Priority.ALWAYS)}))
	protected SimpleStringProperty code;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=250)
	protected SimpleStringProperty description;
	
	public DocumentStatusMV(DocumentStatus entity) {
		super(entity);
		super.formatToString(TFormatter.create()
				.add("%s", code)
				.add(" %s", name));
	}

}
