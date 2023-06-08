/**
 * 
 */
package org.tedros.person.module.customer.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
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
import org.tedros.person.model.ClientCompany;
import org.tedros.person.model.ClientCompanyStatus;
import org.tedros.person.model.ClientCompanyType;
import org.tedros.person.model.LegalPersonMV;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IPersonController.JNDI_NAME, model=ClientCompany.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonController.JNDI_NAME,
		query = @TQuery(entity=ClientCompany.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.CORPORATE_NAME),
				@TCondition(field = "otherName", operator=TCompareOp.LIKE, label=TUsualKey.TRADE_NAME)},
			orderBy= {@TOrder(label = TUsualKey.CORPORATE_NAME, field = "name"),
					@TOrder(label = TUsualKey.TRADE_NAME, field = "otherName")}
				),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_CLIENT_COMPANY,
		buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.CLIENT_COMPANY_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_CUSTOMER, viewName = PersonKeys.VIEW_CLIENT_COMPANY,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ClientCompanyMV extends  LegalPersonMV<ClientCompany> {

	@TTabPane(tabs = { 
		@TTab(text = TUsualKey.MAIN_DATA,
			content = @TContent(detailForm=@TDetailForm(fields={"otherName","type", "address"}))),
		@TTab(text = TUsualKey.DESCRIPTION, 
			content = @TContent(detailForm=@TDetailForm(fields={"description"}))),
		@TTab(text = TUsualKey.OBSERVATION, 
			content = @TContent(detailForm=@TDetailForm(fields={"observation"}))), 
		@TTab(text = TUsualKey.EVENTS,
		content = @TContent(detailForm=@TDetailForm(fields={"events"})))
	})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IPersonTypeController.JNDI_NAME, 
	optionModelViewClass=ClientCompanyTypeMV.class,
	entityClass=ClientCompanyType.class))
	@THBox(	spacing=10, fillHeight=true,
			pane=@TPane(children={"type", "status"}), 
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER)}))
	private SimpleObjectProperty<ClientCompanyType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IPersonStatusController.JNDI_NAME, 
	optionModelViewClass=ClientCompanyStatusMV.class,
	entityClass=ClientCompanyStatus.class))
	private SimpleObjectProperty<ClientCompanyStatus> status;
	
	public ClientCompanyMV(ClientCompany entity) {
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

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

	public SimpleObjectProperty<ClientCompanyType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<ClientCompanyType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<ClientCompanyStatus> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<ClientCompanyStatus> status) {
		this.status = status;
	}

}
