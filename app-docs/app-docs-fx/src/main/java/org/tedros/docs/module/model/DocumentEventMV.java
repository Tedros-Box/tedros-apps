/**
 * 
 */
package org.tedros.docs.module.model;

import java.util.Date;

import org.tedros.docs.model.DocumentEvent;
import org.tedros.extension.contact.model.ContactMV;
import org.tedros.extension.model.Contact;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TDetailForm;
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
import org.tedros.fx.presenter.entity.behavior.TDetailCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TDetailCrudViewDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "#{form.docs.event}", showBreadcrumBar=true, scroll=false)
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, 
buildModesRadioButton=false, viewTitle="#{view.docs.event}")))
public class DocumentEventMV extends TEntityModelView<DocumentEvent> {
	
	@TTabPane(tabs = { 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"hboxPane"})), text = "#{label.main.data}"), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"content"})), text = "#{label.content}")
	})
	private SimpleLongProperty id;
	
	@THBox(	pane=@TPane(children={"title","description"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
								@TPriority(field="description", priority=Priority.ALWAYS)}))
	private SimpleStringProperty hboxPane;
	
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	@TVBox(	pane=@TPane(children={"title","dateEvent","contacts"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
						@TPriority(field="dateEvent", priority=Priority.ALWAYS),
						@TPriority(field="contacts", priority=Priority.ALWAYS)}))
	private SimpleStringProperty title;

	@TLabel(text="#{label.date}")
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> dateEvent;
	
	@TLabel(text="#{label.contacts}")
	@TEditEntityModal(height=60, modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TLabel(text="#{label.description}")
	@TTextAreaField(maxLength=250, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	@THTMLEditor(control=@TControl( maxHeight=400, parse = true))
	private SimpleStringProperty content;
	
	public DocumentEventMV(DocumentEvent entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return title;
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	public SimpleObjectProperty<Date> getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(SimpleObjectProperty<Date> dateEvent) {
		this.dateEvent = dateEvent;
	}

	public ITObservableList<ContactMV> getContacts() {
		return contacts;
	}

	public void setContacts(ITObservableList<ContactMV> contacts) {
		this.contacts = contacts;
	}

	public SimpleStringProperty getContent() {
		return content;
	}

	public void setContent(SimpleStringProperty content) {
		this.content = content;
	}

	public SimpleStringProperty getHboxPane() {
		return hboxPane;
	}

	public void setHboxPane(SimpleStringProperty hboxPane) {
		this.hboxPane = hboxPane;
	}

}
