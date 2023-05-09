/**
 * 
 */
package org.tedros.samples.module.sale.setting;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.form.TSetting;
import org.tedros.samples.module.sale.model.SaleItemMV;
import org.tedros.samples.module.sale.model.SaleMV;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.text.Text;

/**
 * @author Davis Gordon
 *
 */
public class SaleSetting extends TSetting {

	private TRepository repo;
	
	/**
	 * @param descriptor
	 */
	public SaleSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		this.repo = new TRepository();
	}

	@Override
	public void dispose() {
		repo.clear();
	}

	@Override
	public void run() {
		SaleMV m = super.getModelView();
		//Getting the property from model view
		ITObservableList<SaleItemMV> items = m.getItems();
		ChangeListener<Number> itemsChl = (a,o,n)->{
			calcTotal(items);
		};
		repo.add("itemsChl", itemsChl);
		items.tHashCodeProperty().addListener(new WeakChangeListener<>(itemsChl));
		
		calcTotal(items);
		
	}

	private void calcTotal(ITObservableList<SaleItemMV> items) {
		BigDecimal v = BigDecimal.ZERO;
		for(SaleItemMV i : items)
			if(i.getTotal().getValue()!=null)
				v = v.add(i.getTotal().getValue());
		Locale l = new Locale(TLanguage.getLocale().toLanguageTag(), TedrosContext.getCountryIso2());
		String t = NumberFormat
		    		.getCurrencyInstance(l)
		    		.format(v);
		Text total = super.getControl("total");
		total.setText(t);
	}

}
