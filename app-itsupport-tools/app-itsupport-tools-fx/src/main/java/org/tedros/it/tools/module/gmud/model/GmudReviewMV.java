package org.tedros.it.tools.module.gmud.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
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
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.GmudReviewStatus;
import org.tedros.it.tools.entity.GmudReview;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

@TForm(scroll = false)
@TDetailTableViewPresenter(
		tableView = @TTableView(
			control=@TControl(minHeight=400, parse = true),
			columns = 
			{
				@TTableColumn(text = ItToolsKey.REVIEWER, cellValue="reviewer"),  
				@TTableColumn(text = TUsualKey.COMMENT, cellValue="comments"), 
				@TTableColumn(text = TUsualKey.STATUS, cellValue="status"), 
				@TTableColumn(text = ItToolsKey.REVIEW_DATE, cellValue="reviewDate", 
						cellFactory=@TCellFactory(parse = true, 
						callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class)))
			}))
public class GmudReviewMV extends TEntityModelView<GmudReview>{
	
	@TLabel(text=ItToolsKey.REVIEWER)
	@TAutoCompleteEntity(required = true,
			control = @TControl(minWidth = 400, parse = true),
			service = IEmployeeController.JNDI_NAME,
			query = @TQuery(entity = Employee.class, 
			condition = {
					@TCondition(field = "name", operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.OR, field = "lastName", operator=TCompareOp.LIKE)}))

	@THBox(pane=@TPane(children={"reviewer", "status"}),
		hgrow = @THGrow(priority = {@TPriority(field = "reviewer", priority = Priority.NEVER),
				@TPriority(field = "status", priority = Priority.NEVER)}))
    private SimpleObjectProperty<Employee> reviewer;
	
	@TLabel(text=TUsualKey.STATUS)
	@TShowField
    private SimpleStringProperty status;

	public GmudReviewMV(GmudReview entity) {
		super(entity);
		if(entity.isNew()) {
			status.set(GmudReviewStatus.PENDING.getDescription());
		}
	}

	public SimpleObjectProperty<Employee> getReviewer() {
		return reviewer;
	}

	public void setReviewer(SimpleObjectProperty<Employee> reviewer) {
		this.reviewer = reviewer;
	}

	public SimpleStringProperty getStatus() {
		return status;
	}

	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}	
}
