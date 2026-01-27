package org.tedros.it.tools.module.gmud.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCheckBoxField;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.domain.TValidateNumber;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.entity.GmudItem;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

@TForm(scroll = false)
@TDetailTableViewPresenter(
	tableView = @TTableView(
		control=@TControl(minHeight=400, parse = true),
		columns = 
		{
			@TTableColumn(text = "Ordem", cellValue="stepOrder"),  
			@TTableColumn(text = "Ação", cellValue="action"), 
			@TTableColumn(text = "Responsavel", cellValue="responsible"), 
			@TTableColumn(text = "Executado", cellValue="completed")
		}))
public class GmudItemMV extends TEntityModelView<GmudItem> {
	
	@TLabel(text="Ordem de Execução")
	@TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO)
	@THBox(pane=@TPane(children={"stepOrder", "responsible","completed"}),
		hgrow = @THGrow(priority = {@TPriority(field = "stepOrder",priority = Priority.NEVER),
				@TPriority(field = "completed",priority = Priority.ALWAYS),
				@TPriority(field = "responsible",priority = Priority.SOMETIMES)}))
	private SimpleIntegerProperty stepOrder;
	
	@TLabel(text="Responsável")
	@TTextField
    private SimpleStringProperty responsible;
	
	@TLabel(text="Executado")
	@TCheckBoxField(labeled = @TLabeled(parse = true, text=TUsualKey.ACTIVE))
    private SimpleBooleanProperty completed;
    
    @TLabel(text="Ação")
	@TTextAreaField(prefRowCount = 4, wrapText = true, required = true)
    private SimpleStringProperty action;

	public GmudItemMV(GmudItem entity) {
		super(entity);
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

	public SimpleStringProperty getResponsible() {
		return responsible;
	}

	public void setResponsible(SimpleStringProperty responsible) {
		this.responsible = responsible;
	}

	public SimpleBooleanProperty getCompleted() {
		return completed;
	}

	public void setCompleted(SimpleBooleanProperty completed) {
		this.completed = completed;
	}

}
