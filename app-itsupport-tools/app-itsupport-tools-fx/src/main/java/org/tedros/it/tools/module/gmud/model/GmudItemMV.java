package org.tedros.it.tools.module.gmud.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
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
import org.tedros.fx.domain.TValidateNumber;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.domain.GmudItemStatus;
import org.tedros.it.tools.entity.GmudItem;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

@TForm(scroll = false)
@TDetailTableViewPresenter(
	tableView = @TTableView(
		control=@TControl(minHeight=400, parse = true),
		columns = 
		{
			@TTableColumn(text = "Ordem", cellValue="stepOrder"),  
			@TTableColumn(text = TUsualKey.ACTION, cellValue="action"), 
			@TTableColumn(text = "Responsavel", cellValue="responsible"), 
			@TTableColumn(text = TUsualKey.STATUS, cellValue="status")
		}))
public class GmudItemMV extends TEntityModelView<GmudItem> {
	
	@TLabel(text="Ordem de Execução")
	@TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO)
	@THBox(pane=@TPane(children={"stepOrder", "responsible","status"}),
		hgrow = @THGrow(priority = {@TPriority(field = "stepOrder",priority = Priority.NEVER),
				@TPriority(field = "status", priority = Priority.ALWAYS),
				@TPriority(field = "responsible",priority = Priority.SOMETIMES)}))
	private SimpleIntegerProperty stepOrder;
	
	@TLabel(text="Responsável")
	@TAutoCompleteEntity(required = true,
	control = @TControl(minWidth = 400, parse = true),
	service = IEmployeeController.JNDI_NAME,
	query = @TQuery(entity = Employee.class, 
	condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE), 
			@TCondition(logicOp=TLogicOp.OR, field = "lastName", operator=TCompareOp.LIKE)}))
    private SimpleObjectProperty<Employee> responsible;
	
	@TLabel(text=TUsualKey.STATUS)
	@TShowField
    private SimpleStringProperty status;
    
    @TLabel(text=TUsualKey.ACTION)
	@TTextAreaField(prefRowCount = 6, maxLength = 1000, 
	wrapText = true, required = true)
    private SimpleStringProperty action;

	public GmudItemMV(GmudItem entity) {
		super(entity);
		if(entity.isNew()) {
			status.set(GmudItemStatus.PENDING.getDescription());
		}
	}

	public SimpleIntegerProperty getStepOrder() {
		return stepOrder;
	}

	public void setStepOrder(SimpleIntegerProperty stepOrder) {
		this.stepOrder = stepOrder;
	}

	public SimpleStringProperty getAction() {
		return action;
	}

	public void setAction(SimpleStringProperty action) {
		this.action = action;
	}

	public SimpleObjectProperty<Employee> getResponsible() {
		return responsible;
	}

	public void setResponsible(SimpleObjectProperty<Employee> responsible) {
		this.responsible = responsible;
	}

	public SimpleStringProperty getStatus() {
		return status;
	}

	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}

}
