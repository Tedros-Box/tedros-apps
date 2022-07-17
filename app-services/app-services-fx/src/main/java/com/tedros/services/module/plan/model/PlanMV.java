/**
 * 
 */
package com.tedros.services.module.plan.model;

import java.math.BigDecimal;
import java.util.Date;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TRadioButton;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.services.ServKey;
import com.tedros.services.converter.StatusConverter;
import com.tedros.services.domain.DomainApp;
import com.tedros.services.domain.Status;
import com.tedros.services.ejb.controller.IPlanController;
import com.tedros.services.model.PaymentPlan;
import com.tedros.services.model.Plan;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IPlanController.JNDI_NAME, model=Plan.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Plan.class, serviceName = IPlanController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_PLAN,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.PLAN_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_PLANS, viewName = ServKey.VIEW_PLAN,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PlanMV extends TEntityModelView<Plan> {

	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=250, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true)
	@THBox(	pane=@TPane(children={"payments", "description"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="payments", priority=Priority.NEVER), 
			@TPriority(field="description", priority=Priority.ALWAYS)}))
	private SimpleStringProperty description;

	@TLabel(text=TUsualKey.PAYMENT_PLANS)
	@TEditEntityModal(modalHeight=380, modalWidth=480,
		modelClass = PaymentPlan.class, modelViewClass=PaymentPlanMV.class)
	@TVBox(	pane=@TPane(children={"registrationFee","beginDate","payments"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="payments", priority=Priority.NEVER), 
			@TPriority(field="registrationFee", priority=Priority.NEVER), 
			@TPriority(field="beginDate", priority=Priority.NEVER)}))
	@TModelViewType(modelClass = PaymentPlan.class, modelViewClass=PaymentPlanMV.class)
	private ITObservableList<PaymentPlanMV> payments;
	
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
	
	
	public PlanMV(Plan entity) {
		super(entity);
	}


	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
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


	public ITObservableList<PaymentPlanMV> getPayments() {
		return payments;
	}

	public void setPayments(ITObservableList<PaymentPlanMV> payments) {
		this.payments = payments;
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
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}
