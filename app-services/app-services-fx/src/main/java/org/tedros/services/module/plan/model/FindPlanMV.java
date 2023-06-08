/**
 * 
 */
package org.tedros.services.module.plan.model;

import java.math.BigDecimal;
import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TBigDecimalField;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TCellValueFactory;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.control.tablecell.TCurrencyCallback;
import org.tedros.fx.control.tablecell.TShortDateCallback;
import org.tedros.fx.presenter.modal.behavior.TSelectionModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TSelectionModalDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.services.ServKey;
import org.tedros.services.converter.StatusConverter;
import org.tedros.services.domain.Status;
import org.tedros.services.ejb.controller.IPlanController;
import org.tedros.services.model.Plan;
import org.tedros.services.module.plan.table.PlanItemMV;
import org.tedros.services.module.plan.table.StatusCellCallBack;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = ServKey.VIEW_PLAN)
@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
	paginator=@TPage(query=@TQuery(entity = Plan.class), modelView=PlanItemMV.class, 
		serviceName = IPlanController.JNDI_NAME),
	presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
		decorator = @TDecorator(type=TSelectionModalDecorator.class, viewTitle=ServKey.VIEW_PLAN)),
	tableView=@TTableView(editable=true, 
		columns = {@TTableColumn(cellValue="name", text = TUsualKey.NAME, resizable=true), 
			@TTableColumn(cellValue="registrationFee", text = TUsualKey.REGISTRATION_FEE, resizable=true,
					cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TCurrencyCallback.class))), 
			@TTableColumn(cellValue="value", text =TUsualKey.VALUE, resizable=true,
					cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TCurrencyCallback.class))), 
			@TTableColumn(cellValue="beginDate", text =TUsualKey.BEGIN_DATE, resizable=true,
					cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))), 
			@TTableColumn(cellValue="endDate", text =TUsualKey.END_DATE, resizable=true,
					cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))), 
			@TTableColumn(cellValue="status", text =TUsualKey.STATUS, resizable=true,
			cellValueFactory=@TCellValueFactory(parse=true, 
			value=@TCallbackFactory(parse=true, value=StatusCellCallBack.class)))
		}), 
	allowsMultipleSelections = true)
public class FindPlanMV extends TEntityModelView<Plan> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=250, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	
	@TLabel(text=TUsualKey.REGISTRATION_FEE)
	@TBigDecimalField
	@THBox(	pane=@TPane(children={"registrationFee", "value"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="registrationFee", priority=Priority.ALWAYS), 
			@TPriority(field="value", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<BigDecimal> registrationFee;
	
	@TLabel(text=TUsualKey.VALUE)
	@TBigDecimalField
	private SimpleObjectProperty<BigDecimal> value;
	
	@TLabel(text=TUsualKey.BEGIN_DATE)
	@TDatePickerField
	@THBox(	pane=@TPane(children={"beginDate", "endDate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="beginDate", priority=Priority.ALWAYS), 
			@TPriority(field="endDate", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Date> beginDate;
	
	@TLabel(text=TUsualKey.END_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> endDate;

	@TLabel(text=TUsualKey.STATUS)
	@THorizontalRadioGroup(spacing= 10,
	converter=@TConverter(parse = true, type = StatusConverter.class),
	radioButtons = { @TRadioButton(text = TUsualKey.ENABLED, userData = TUsualKey.ENABLED),
			@TRadioButton(text = TUsualKey.DISABLED, userData = TUsualKey.DISABLED),
			@TRadioButton(text = TUsualKey.SUSPENDED, userData = TUsualKey.SUSPENDED)
	})
	private SimpleObjectProperty<Status> status;
	
	
	public FindPlanMV(Plan entity) {
		super(entity);
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleObjectProperty<BigDecimal> getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(SimpleObjectProperty<BigDecimal> registrationFee) {
		this.registrationFee = registrationFee;
	}

	public SimpleObjectProperty<BigDecimal> getValue() {
		return value;
	}

	public void setValue(SimpleObjectProperty<BigDecimal> value) {
		this.value = value;
	}

	public SimpleObjectProperty<Date> getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(SimpleObjectProperty<Date> beginDate) {
		this.beginDate = beginDate;
	}

	public SimpleObjectProperty<Date> getEndDate() {
		return endDate;
	}

	public void setEndDate(SimpleObjectProperty<Date> endDate) {
		this.endDate = endDate;
	}

	public SimpleObjectProperty<Status> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<Status> status) {
		this.status = status;
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

}
