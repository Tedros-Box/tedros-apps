package org.tedros.it.tools.module.gmud.model;

import java.util.Date;
import java.util.List;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.ejb.controller.IGmudController;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.entity.GmudIssueReference;
import org.tedros.it.tools.entity.GmudItem;
import org.tedros.it.tools.entity.GmudReview;
import org.tedros.it.tools.module.gmud.builder.GmudStatusBuilder;
import org.tedros.it.tools.module.gmud.builder.GmudTypesBuilder;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IGmudController.JNDI_NAME, model=Gmud.class)
@TListViewPresenter(
		page=@TPage(serviceName = IGmudController.JNDI_NAME, 
			query = @TQuery(entity=Gmud.class, 
					condition = {	@TCondition(field = "id", operator=TCompareOp.EQUAL, label="Numero"),
									@TCondition(field = "title", operator=TCompareOp.LIKE, label=TUsualKey.TITLE),
									@TCondition(field = "type", operator=TCompareOp.LIKE, label=TUsualKey.TYPE),
									@TCondition(field = "status", operator=TCompareOp.LIKE, label=TUsualKey.STATUS)},
					orderBy = {	@TOrder(label ="Numero", field = "id"),
								@TOrder(label = TUsualKey.TITLE, field = "title"),
								@TOrder(label = TUsualKey.TYPE, field = "type"),
								@TOrder(label = TUsualKey.STATUS, field = "status")}),
			showSearch=true, showOrderBy=true),
		
		presenter=@TPresenter(
			decorator = @TDecorator(viewTitle="Editar GMUD", buildModesRadioButton=false),
			behavior=@TBehavior(runNewActionAfterSave=false, saveAllModels=false, saveOnlyChangedModels=false)))
public class EditGmudMV extends TEntityModelView<Gmud> {
	
	@TTabPane(tabs = { 
			@TTab(text = TUsualKey.MAIN_DATA, 	fields={"title"}), 
			@TTab(text = "Rollback plan",	fields={"rollbackPlan"}),
			@TTab(text = "Plano de execução", 	fields={"executionPlan"}),
			@TTab(text = "Revisores", 	fields={"reviews"})
			})
		private SimpleLongProperty id;
	
	@TLabel(text="Title")
	@TTextField(maxLength=150, required=true)
	@TVBox(pane=@TPane(children={"title", "type", "description"}))
	private SimpleStringProperty title;
	
	@TLabel(text="Type")
	@TComboBoxField(items=GmudTypesBuilder.class, required=true)
	@TFlowPane(hgap=20, vgap=12,
		pane=@TPane(children={"type", "status", "requester", "scheduledDate"}))	
    private SimpleObjectProperty<String> type;

	@TLabel(text="Status")
	@TComboBoxField(items=GmudStatusBuilder.class, required=true)
    private SimpleObjectProperty<String> status;

	@TLabel(text="Requisitante")
	@TAutoCompleteEntity(required = true,
			control = @TControl(minWidth = 400, parse = true),
			service = IEmployeeController.JNDI_NAME,
			query = @TQuery(entity = Employee.class, 
			condition = {
					@TCondition(field = "name", operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.OR, field = "lastName", operator=TCompareOp.LIKE)}))
    private SimpleObjectProperty<Employee> requester;

	@TLabel(text=ItToolsKey.EXECUTION_DATE)
	@TDatePickerField()
    private SimpleObjectProperty<Date> scheduledDate;
	
    private SimpleLongProperty projectId;
    private SimpleStringProperty projectName;

	@TLabel(text="Description")
	@THTMLEditor(control=@TControl( maxHeight=450, parse = true), required = true)
    private SimpleStringProperty description;

	@THTMLEditor(control=@TControl( maxHeight=450, parse = true), required = true)
    private SimpleStringProperty rollbackPlan;
	
	private ITObservableList<GmudIssueReference> issueReferences;
	
	@TFieldBox(node=@TNode(id="list", parse = true))
	@TDetailListField(region=@TRegion(maxHeight=400, parse = true), required = true,
		modelView = GmudItemMV.class, entity = GmudItem.class)
	@TGenericType(model=GmudItem.class, modelView=GmudItemMV.class)
    private ITObservableList<GmudItemMV> executionPlan;

	@TFieldBox(node=@TNode(id="list", parse = true))
	@TDetailListField(region=@TRegion(maxHeight=400, parse = true), required = true,
		modelView = GmudReviewMV.class, entity = GmudReview.class)
	@TGenericType(model=GmudReview.class, modelView=GmudReviewMV.class)
    private ITObservableList<GmudReviewMV> reviews;

	public EditGmudMV(Gmud entity) {
		super(entity);	
		super.formatToString("[%s][%s][%s] %s", id, type, status, title);
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleObjectProperty<String> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<String> type) {
		this.type = type;
	}

	public SimpleObjectProperty<String> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<String> status) {
		this.status = status;
	}

	public SimpleObjectProperty<Employee> getRequester() {
		return requester;
	}

	public void setRequester(SimpleObjectProperty<Employee> requester) {
		this.requester = requester;
	}

	public SimpleObjectProperty<Date> getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(SimpleObjectProperty<Date> scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public SimpleStringProperty getRollbackPlan() {
		return rollbackPlan;
	}

	public void setRollbackPlan(SimpleStringProperty rollbackPlan) {
		this.rollbackPlan = rollbackPlan;
	}

	public ITObservableList<GmudItemMV> getExecutionPlan() {
		return executionPlan;
	}

	public void setExecutionPlan(ITObservableList<GmudItemMV> executionPlan) {
		this.executionPlan = executionPlan;
	}

	public ITObservableList<GmudReviewMV> getReviews() {
		return reviews;
	}

	public void setReviews(ITObservableList<GmudReviewMV> reviews) {
		this.reviews = reviews;
	}

}
