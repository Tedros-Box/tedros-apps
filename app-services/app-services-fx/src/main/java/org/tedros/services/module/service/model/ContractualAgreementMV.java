/**
 * 
 */
package org.tedros.services.module.service.model;

import java.math.BigDecimal;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TBigDecimalField;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TEditModalPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.domain.TZeroValidation;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.services.ejb.controller.IContractualAgreementController;
import org.tedros.services.ejb.controller.IServiceTypeController;
import org.tedros.services.model.ContractualAgreement;
import org.tedros.services.model.ServiceType;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter(listViewMaxWidth=150, listViewMinWidth=150)
@TEjbService(model = ContractualAgreement.class, serviceName = IContractualAgreementController.JNDI_NAME)
public class ContractualAgreementMV extends TEntityModelView<ContractualAgreement> {
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	@THBox(	pane=@TPane(children={"name","serviceType"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={
			@TPriority(field="name", priority=Priority.NEVER),
			@TPriority(field="serviceType", priority=Priority.NEVER)}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.SERVICE_TYPE)
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IServiceTypeController.JNDI_NAME, 
	optionModelViewClass=ServiceTypeMV.class,
	entityClass=ServiceType.class))
	private SimpleObjectProperty<ServiceType> serviceType;

	@TLabel(text=TUsualKey.AMOUNT)
	@TIntegerField(zeroValidation=TZeroValidation.NONE)
	private SimpleIntegerProperty amount;
	
	@TLabel(text=TUsualKey.VALUE)
	@TBigDecimalField
	private SimpleObjectProperty<BigDecimal> value;
	
	@TLabel(text=TUsualKey.PERCENTAGE)
	@TDoubleField
	private SimpleDoubleProperty percentage;
	
	public ContractualAgreementMV(ContractualAgreement entity) {
		super(entity);
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleObjectProperty<ServiceType> getServiceType() {
		return serviceType;
	}

	public void setServiceType(SimpleObjectProperty<ServiceType> serviceType) {
		this.serviceType = serviceType;
	}

	public SimpleIntegerProperty getAmount() {
		return amount;
	}

	public void setAmount(SimpleIntegerProperty amount) {
		this.amount = amount;
	}

	public SimpleObjectProperty<BigDecimal> getValue() {
		return value;
	}

	public void setValue(SimpleObjectProperty<BigDecimal> value) {
		this.value = value;
	}

	public SimpleDoubleProperty getPercentage() {
		return percentage;
	}

	public void setPercentage(SimpleDoubleProperty percentage) {
		this.percentage = percentage;
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}
	
}