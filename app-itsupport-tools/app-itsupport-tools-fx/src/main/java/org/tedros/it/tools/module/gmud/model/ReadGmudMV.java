package org.tedros.it.tools.module.gmud.model;

import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TTimeStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.entity.GmudIssueReference;
import org.tedros.it.tools.entity.GmudItem;
import org.tedros.it.tools.entity.GmudReview;
import org.tedros.person.model.Employee;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;

@TForm(editCssId = "t-rounded-form")
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TViewDecorator.class, 
			buildModesRadioButton=false),
			behavior=@TBehavior(type=TViewBehavior.class))
public class ReadGmudMV extends TEntityModelView<Gmud> {
	
	@TTabPane(tabs = { 
			@TTab(text = TUsualKey.MAIN_DATA, fields={"id", "type", "description"}), 
			@TTab(text = ItToolsKey.GMUD_REDMINE_REFERENCE, fields={"issueReferences"}),
			@TTab(text = ItToolsKey.GMUD_ROLLBACK_PLAN, fields={"rollbackPlan"}),
			@TTab(text = ItToolsKey.GMUD_EXECUTION_PLAN, fields={"executionPlan"}),
			@TTab(text = ItToolsKey.GMUD_ADD_REVIEWER, fields={"reviews"})
			})			
	private SimpleStringProperty tab;
	
	@TLabel(text=TUsualKey.NUMBER)
	@TShowField
	@THBox(pane=@TPane(children={"id", "title"}), spacing = 10,
		hgrow = @THGrow(priority = {
				@TPriority(field = "id", priority = Priority.NEVER),
				@TPriority(field = "title", priority = Priority.ALWAYS)}))
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.TITLE)
	@TShowField
	private SimpleStringProperty title;
	
	@TLabel(text=TUsualKey.TYPE)
	@TShowField
	@TFlowPane(hgap=20, vgap=12,
		pane=@TPane(children={"type", "status", "requester", "projectName", "scheduledDate"}))	
    private SimpleObjectProperty<String> type;

	@TLabel(text=TUsualKey.STATUS)
	@TShowField
    private SimpleObjectProperty<String> status;
	
	@TShowField(fields = {@TField(name = "name", label = TUsualKey.REQUESTER)})
    private SimpleObjectProperty<Employee> requester;

	@TLabel(text=ItToolsKey.EXECUTION_DATE)
	@TShowField(fields = {@TField(timeStyle = TTimeStyle.MEDIUM)})
    private SimpleObjectProperty<Date> scheduledDate;    
    
	@TLabel(text=ItToolsKey.GITLAB_PROJECT)
	@TShowField
    private SimpleStringProperty projectName;

	@TLabel(text=TUsualKey.DESCRIPTION)
	@THTMLEditor(control=@TControl( maxHeight=450, parse = true))
    private SimpleStringProperty description;

	@THTMLEditor(control=@TControl( maxHeight=450, parse = true), required = true)
    private SimpleStringProperty rollbackPlan;
	
	@TFieldBox(node=@TNode(id="list", parse = true))
	@TShowField(fields = {
			@TField(name = "issueId", label = ItToolsKey.ISSUE_ID),
			@TField(name = "issueTitle", label = TUsualKey.TITLE)})
	@TGenericType(model=GmudIssueReference.class)
	private ITObservableList<GmudIssueReference> issueReferences;
	
	@TFieldBox(node=@TNode(id="list", parse = true))
	@TShowField(fields = {
			@TField(name = "stepOrder", label = ItToolsKey.EXECUTION_ORDER),
			@TField(name = "action", label = TUsualKey.ACTION),
			@TField(name = "responsible", label = TUsualKey.RESPONSABLE)})
	@TGenericType(model=GmudItem.class)
    private ObservableList<GmudItem> executionPlan;

	@TFieldBox(node=@TNode(id="list", parse = true))
	@TShowField(fields = {
			@TField(name = "reviewer", label = ItToolsKey.EXECUTION_ORDER),
			@TField(name = "comments", label = TUsualKey.COMMENT),
			@TField(name = "status", label = TUsualKey.STATUS),
			@TField(name = "reviewDate", label = ItToolsKey.REVIEW_DATE, timeStyle = TTimeStyle.MEDIUM)})
	@TGenericType(model=GmudReview.class)
    private ObservableList<GmudReview> reviews;

	public ReadGmudMV(Gmud entity) {
		super(entity);	
		super.formatToString("[%s][%s][%s] %s", id, type, status, title);
	}
}
