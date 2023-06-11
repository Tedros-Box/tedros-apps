/**
 * 
 */
package org.tedros.extension.module.doc.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.ExtKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.extension.model.DocType;
import org.tedros.extension.model.DocumentType;
import org.tedros.extension.model.ExtensionDomainMV;
import org.tedros.extension.module.doc.converter.DocTypeConverter;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.assistant.TAiAssistant;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TRadioButton;
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
import org.tedros.fx.model.TFormatter;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = ExtKey.FORM_DOCS_TYPE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IExtensionDomainController.JNDI_NAME, model=DocumentType.class)
@TListViewPresenter(
	aiAssistant=@TAiAssistant(jsonModel = DocumentTypeJsonModel.class, show=true, 
		modelViewClass = DocumentTypeMV.class),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=ExtKey.VIEW_DOCS_TYPE),
		behavior = @TBehavior(runNewActionAfterSave=true)))
@TSecurity(id=DomainApp.DOCUMENT_TYPE_FORM_ID, 
	appName = ExtKey.APP_EXTS, moduleName = ExtKey.MODULE_DOCS, viewName = ExtKey.VIEW_DOCS_TYPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DocumentTypeMV extends ExtensionDomainMV<DocumentType> {

	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id=TFieldBox.TITLE, parse = true))
	@TText(text=ExtKey.HEADER_DOCS_TYPE, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;

	@TLabel(text=TUsualKey.CODE)
	@TTextField(maxLength=20)
	@THBox(	pane=@TPane(children={"code","name"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={
					@TPriority(field="code", priority=Priority.NEVER),
					@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty code;

	@TLabel(text=TUsualKey.TYPE)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = DocTypeConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.TAX_NUMBER, userData = TUsualKey.TAX_NUMBER ),
				@TRadioButton(text = TUsualKey.ID_NUMBER, userData = TUsualKey.ID_NUMBER ),
				@TRadioButton(text = TUsualKey.OTHER, userData = TUsualKey.OTHER )
		})
	private SimpleObjectProperty<DocType> docType;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=250)
	private SimpleStringProperty description;
	
	public DocumentTypeMV(DocumentType entity) {
		super(entity);
		super.formatToString(TFormatter.create()
				.add("%s", code)
				.add(" %s", name));
	}

}
