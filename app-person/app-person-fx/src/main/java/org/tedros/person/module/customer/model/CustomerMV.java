/**
 * 
 */
package org.tedros.person.module.customer.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.ejb.controller.IPersonStatusController;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.Customer;
import org.tedros.person.model.CustomerStatus;
import org.tedros.person.model.CustomerType;
import org.tedros.person.model.NaturalPersonMV;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author Davis Gordon
 *
 */

@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IPersonController.JNDI_NAME, model=Customer.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonController.JNDI_NAME,
		query = @TQuery(entity=Customer.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME),
				@TCondition(field = "lastName", operator=TCompareOp.LIKE, label=TUsualKey.LAST_NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name"),
					@TOrder(label = TUsualKey.LAST_NAME, field = "lastName")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_CUSTOMERS)))
@TSecurity(id=DomainApp.CUSTOMER_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_CUSTOMER, viewName = PersonKeys.VIEW_CUSTOMERS,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CustomerMV extends NaturalPersonMV<Customer> {

	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA, scroll=true, fields={"lastName","sex", "type", "address"}),
		@TTab(text = TUsualKey.DESCRIPTION, fields={"description"}),
		@TTab(text = TUsualKey.OBSERVATION, fields={"observation"}), 
		@TTab(text = TUsualKey.EVENTS,fields={"events"})
	})
	private SimpleLongProperty id;

	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(
	process=@TProcess(service = IPersonTypeController.JNDI_NAME, 
	modelView=CustomerTypeMV.class, query=@TQuery(entity=CustomerType.class)))
	@TFlowPane(hgap=HGAP, vgap=VGAP,
	pane=@TPane(children={"type", "status"}))
	private SimpleObjectProperty<CustomerType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(
	process=@TProcess(service = IPersonStatusController.JNDI_NAME, 
	modelView=CustomerStatusMV.class, query=@TQuery(entity=CustomerStatus.class)))
	private SimpleObjectProperty<CustomerStatus> status;
	
	public CustomerMV(Customer entity) {
		super(entity);
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleObjectProperty<CustomerType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<CustomerType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<CustomerStatus> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<CustomerStatus> status) {
		this.status = status;
	}

}
