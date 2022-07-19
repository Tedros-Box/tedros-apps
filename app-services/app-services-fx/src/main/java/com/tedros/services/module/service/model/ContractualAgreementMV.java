/**
 * 
 */
package com.tedros.services.module.service.model;

import java.math.BigDecimal;

import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TDoubleField;
import com.tedros.fxapi.annotation.control.TIntegerField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.services.ejb.controller.IContractualAgreementController;
import com.tedros.services.ejb.controller.IServiceTypeController;
import com.tedros.services.model.ContractualAgreement;
import com.tedros.services.model.ServiceType;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
	
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	@THBox(	pane=@TPane(children={"name","serviceType"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={
			@TPriority(field="name", priority=Priority.NEVER),
			@TPriority(field="serviceType", priority=Priority.NEVER)}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.SERVICE_TYPE)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
	optionsList=@TOptionsList(serviceName = IServiceTypeController.JNDI_NAME, 
	optionModelViewClass=ServiceTypeMV.class,
	entityClass=ServiceType.class))
	private SimpleObjectProperty<ServiceType> serviceType;
	

	@TLabel(text=TUsualKey.AMOUNT)
	@TIntegerField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
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
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}
	
}
