/**
 * 
 */
package org.tedros.extension.module.doc.model;

import java.util.Date;

import org.tedros.extension.ExtKey;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactMV;
import org.tedros.extension.model.DocumentEvent;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TDetailListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.entity.behavior.TDetailCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TDetailCrudViewDecorator;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, 
buildModesRadioButton=false, viewTitle=ExtKey.VIEW_DOCS_EVENT)))
public class DocumentEventMV extends TEntityModelView<DocumentEvent> {
	
	@TTabPane(tabs = { 
		@TTab(closable=false, fields={"hboxPane"}, text = TUsualKey.MAIN_DATA), 
		@TTab(closable=false, fields={"content"}, text = TUsualKey.CONTENT)
	})
	private SimpleLongProperty id;
	
	@THBox(	pane=@TPane(children={"title","description"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
								@TPriority(field="description", priority=Priority.ALWAYS)}))
	private SimpleStringProperty hboxPane;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	@TVBox(	pane=@TPane(children={"title","dateEvent","contacts"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
						@TPriority(field="dateEvent", priority=Priority.ALWAYS),
						@TPriority(field="contacts", priority=Priority.ALWAYS)}))
	private SimpleStringProperty title;

	@TLabel(text=TUsualKey.DATE)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> dateEvent;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(height=60, model = Contact.class, modelView=ContactMV.class)
	@TGenericType(model = Contact.class, modelView=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=250, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	@THTMLEditor(control=@TControl( maxHeight=400, parse = true))
	private SimpleStringProperty content;
	
	public DocumentEventMV(DocumentEvent entity) {
		super(entity);
		super.formatToString("%s", title);
	}

}
