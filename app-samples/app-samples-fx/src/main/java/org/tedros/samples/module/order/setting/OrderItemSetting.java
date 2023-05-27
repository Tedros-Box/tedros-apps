/**
 * 
 */
package org.tedros.samples.module.order.setting;

import java.math.BigDecimal;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.form.TSetting;
import org.tedros.samples.module.order.model.OrderItemMV;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

/**
 * @author Davis Gordon
 *
 */
public class OrderItemSetting extends TSetting {

	private TRepository repo;
	
	/**
	 * @param descriptor
	 */
	public OrderItemSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		this.repo = new TRepository();
	}

	@Override
	public void dispose() {
		repo.clear();
	}

	@Override
	public void run() {
		OrderItemMV m = super.getModelView();
		//Getting the property from model view
		SimpleObjectProperty<BigDecimal> unitPrice = m.getUnitPrice();
		//Other way to getting a property
		SimpleIntegerProperty amount = (SimpleIntegerProperty) super.getProperty("amount");
		
		ChangeListener<BigDecimal> priceChl = (a,o,n)->{
			m.calcTotal();
		};
		repo.add("priceChl", priceChl);
		unitPrice.addListener(new WeakChangeListener<>(priceChl));
		
		ChangeListener<Number> amountChl = (a,o,n)->{
			m.calcTotal();
		};
		repo.add("amountChl", amountChl);
		amount.addListener(new WeakChangeListener<>(amountChl));
	}

}
