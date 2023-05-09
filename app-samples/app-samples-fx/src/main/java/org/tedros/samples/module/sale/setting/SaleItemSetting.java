/**
 * 
 */
package org.tedros.samples.module.sale.setting;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TDoubleField;
import org.tedros.fx.form.TSetting;
import org.tedros.samples.module.sale.model.SaleItemMV;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

/**
 * @author Davis Gordon
 *
 */
public class SaleItemSetting extends TSetting {

	private TRepository repo;
	
	/**
	 * @param descriptor
	 */
	public SaleItemSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		this.repo = new TRepository();
	}

	@Override
	public void dispose() {
		repo.clear();
	}

	@Override
	public void run() {
		SaleItemMV m = super.getModelView();
		//Getting the property from model view
		SimpleObjectProperty<BigDecimal> unitPrice = m.getUnitPrice();
		//Other way to getting a property
		SimpleIntegerProperty amount = (SimpleIntegerProperty) super.getProperty("amount");
		//Getting a component from TFieldBox
		TDoubleField rebate = (TDoubleField) super.getFieldBox("rebate").gettControl();
		SimpleObjectProperty<BigDecimal> total = m.getTotal();

		ChangeListener<BigDecimal> priceChl = (a,o,n)->{
			BigDecimal t = n!=null ? n : BigDecimal.ZERO;
			Integer q = amount.getValue()!=null ? amount.getValue() : 0;
			Double r = rebate.getValue()!=null ? rebate.getValue() : 0;
			t = calcTotal(t, q, r);
			total.setValue(t);
		};
		repo.add("priceChl", priceChl);
		unitPrice.addListener(new WeakChangeListener<>(priceChl));
		
		ChangeListener<Number> amountChl = (a,o,n)->{
			Integer q = n!=null ? (int) n : 0;
			BigDecimal t = unitPrice.getValue()!=null ? unitPrice.getValue() : BigDecimal.ZERO;
			Double r = rebate.getValue()!=null ? rebate.getValue() : 0;
			t = calcTotal(t, q, r);
			total.setValue(t);
		};
		repo.add("amountChl", amountChl);
		amount.addListener(new WeakChangeListener<>(amountChl));
		

		ChangeListener<Double> rebChl = (a,o,n)->{
			Double r = n!=null ? n : 0;
			BigDecimal t = unitPrice.getValue()!=null ? unitPrice.getValue() : BigDecimal.ZERO;
			Integer q = amount.getValue()!=null ? amount.getValue() : 0;
			t = calcTotal(t, q, r);
			total.setValue(t);
		};
		repo.add("rebChl", rebChl);
		rebate.valueProperty().addListener(new WeakChangeListener<>(rebChl));
		
		
	}

	/*10=100%
	x=2%
	100x=10*2
	x=10*2/100*/
	private BigDecimal calcTotal(BigDecimal t, Integer q, Double r) {
		BigDecimal m = t.multiply(new BigDecimal(q));
		BigDecimal v = m.multiply(new BigDecimal(r));
		v = v.divide(new BigDecimal(100), 2, RoundingMode.UNNECESSARY);
		m = m.subtract(v);
		return m;
	}

}
