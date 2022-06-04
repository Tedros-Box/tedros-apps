/**
 * 
 */
package com.tedros.docs.module.model;

import java.util.Date;

import com.tedros.docs.model.DocumentEvent;
import com.tedros.extension.contact.model.ContactMV;
import com.tedros.extension.model.Contact;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.docs.event}", showBreadcrumBar=true, scroll=false)
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, buildModesRadioButton=false, viewTitle="#{view.docs.event}")))
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
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	@TVBox(	pane=@TPane(children={"title","dateEvent","contacts"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
						@TPriority(field="dateEvent", priority=Priority.ALWAYS),
						@TPriority(field="contacts", priority=Priority.ALWAYS)}))
	private SimpleStringProperty title;

	@TReaderHtml
	@TLabel(text="#{label.date}")
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> dateEvent;
	
	@TReaderHtml
	@TLabel(text="#{label.contacts}")
	@TEditEntityModal(height=60, modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TReaderHtml
	@TLabel(text="#{label.description}")
	@TTextAreaField(maxLength=250, wrapText=true, prefRowCount=4)
	@TVBox(	pane=@TPane(children={"description", "warn"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="warn", priority=Priority.ALWAYS), 
						@TPriority(field="description", priority=Priority.ALWAYS)}))
	private SimpleStringProperty description;
	
	@TFieldSet(fields = { "warn", "statusWarning" },
			region=@TRegion(maxWidth=500, parse = true),
			legend = "#{label.warn.process}")
	@TCheckBoxField(labeled = @TLabeled(text="#{label.warn}", parse = true))
	private SimpleStringProperty warn;
	
	@TReaderHtml
	@TLabel(text="#{label.status}")
	@TShowField
	private SimpleStringProperty statusWarning;
	
	
	@TReaderHtml
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
	public SimpleStringProperty getDisplayProperty() {
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

	public SimpleStringProperty getWarn() {
		return warn;
	}

	public void setWarn(SimpleStringProperty warn) {
		this.warn = warn;
	}

	public SimpleStringProperty getStatusWarning() {
		return statusWarning;
	}

	public void setStatusWarning(SimpleStringProperty statusWarning) {
		this.statusWarning = statusWarning;
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
