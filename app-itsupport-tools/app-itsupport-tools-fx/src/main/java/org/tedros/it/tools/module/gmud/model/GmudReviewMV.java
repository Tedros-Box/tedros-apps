package org.tedros.it.tools.module.gmud.model;

import java.util.Date;

import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.control.tablecell.TMediumDateTimeCallback;
import org.tedros.fx.converter.TBooleanToYesNoConverter;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.entity.GmudReview;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

@TForm(scroll = false)
@TDetailTableViewPresenter(
		tableView = @TTableView(
			control=@TControl(minHeight=400, parse = true),
			columns = 
			{
				@TTableColumn(text = "Revisor", cellValue="reviewer"),  
				@TTableColumn(text = "Comentario", cellValue="comments"), 
				@TTableColumn(text = "Aprovado", cellValue="approved"), 
				@TTableColumn(text = "Data revisão", cellValue="reviewDate", 
						cellFactory=@TCellFactory(parse = true, 
						callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class)))
			}))
public class GmudReviewMV extends TEntityModelView<GmudReview>{
	
	@TLabel(text="Revisor")
	@TAutoCompleteEntity(required = true,
			control = @TControl(minWidth = 400, parse = true),
			service = IEmployeeController.JNDI_NAME,
			query = @TQuery(entity = Employee.class, 
			condition = {
					@TCondition(field = "name", operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.OR, field = "lastName", operator=TCompareOp.LIKE)}))

	@THBox(pane=@TPane(children={"reviewer","reviewDate", "approved"}),
		hgrow = @THGrow(priority = {@TPriority(field = "reviewer", priority = Priority.NEVER),
				@TPriority(field = "reviewDate", priority = Priority.NEVER),
				@TPriority(field = "approved", priority = Priority.NEVER)}))
    private SimpleObjectProperty<Employee> reviewer;
	
	@TLabel(text="Data revisão")
	@TShowField
    private SimpleObjectProperty<Date> reviewDate;
	
	@TLabel(text="Aprovado")
	@TShowField(fields = {@TField(name = "approved", converter = TBooleanToYesNoConverter.class)})
    private SimpleBooleanProperty approved;

	@TLabel(text="Comentario")
	@TTextAreaField(prefRowCount = 4, wrapText = true)
    private SimpleStringProperty comments;

	public GmudReviewMV(GmudReview entity) {
		super(entity);
		if(entity.isNew()) {
			reviewDate.set(new Date());
			approved.set(false);
		}
	}

	public SimpleObjectProperty<Employee> getReviewer() {
		return reviewer;
	}

	public void setReviewer(SimpleObjectProperty<Employee> reviewer) {
		this.reviewer = reviewer;
	}

	public SimpleStringProperty getComments() {
		return comments;
	}

	public void setComments(SimpleStringProperty comments) {
		this.comments = comments;
	}

	public SimpleBooleanProperty getApproved() {
		return approved;
	}

	public void setApproved(SimpleBooleanProperty approved) {
		this.approved = approved;
	}

	public SimpleObjectProperty<Date> getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(SimpleObjectProperty<Date> reviewDate) {
		this.reviewDate = reviewDate;
	}

}
