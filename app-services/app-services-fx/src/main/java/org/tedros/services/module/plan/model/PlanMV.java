/**
 * 
 */
package org.tedros.services.module.plan.model;

import java.math.BigDecimal;
import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TBigDecimalField;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.THRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
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
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;
import org.tedros.services.ServKey;
import org.tedros.services.converter.StatusConverter;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.domain.Status;
import org.tedros.services.ejb.controller.IPlanController;
import org.tedros.services.model.PaymentPlan;
import org.tedros.services.model.Plan;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IPlanController.JNDI_NAME, model=Plan.class)
@TListViewPresenter(
	page=@TPage(serviceName = IPlanController.JNDI_NAME,
		query = @TQuery(entity=Plan.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_PLAN,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.PLAN_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_PLANS, viewName = ServKey.VIEW_PLAN,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
		TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PlanMV extends TEntityModelView<Plan> {

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
		model = PaymentPlan.class, modelView=PaymentPlanMV.class)
	@TVBox(	pane=@TPane(children={"registrationFee","beginDate","payments"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="payments", priority=Priority.NEVER), 
			@TPriority(field="registrationFee", priority=Priority.NEVER), 
			@TPriority(field="beginDate", priority=Priority.NEVER)}))
	@TGenericType(model = PaymentPlan.class, modelView=PaymentPlanMV.class)
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
	@THRadioGroup(spacing= 10,
	converter=@TConverter(parse = true, type = StatusConverter.class),
	radio = { @TRadio(text = TUsualKey.ENABLED, userData = TUsualKey.ENABLED),
			@TRadio(text = TUsualKey.DISABLED, userData = TUsualKey.DISABLED),
			@TRadio(text = TUsualKey.SUSPENDED, userData = TUsualKey.SUSPENDED)
	})
	private SimpleObjectProperty<Status> status;
	
	
	public PlanMV(Plan entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

}
