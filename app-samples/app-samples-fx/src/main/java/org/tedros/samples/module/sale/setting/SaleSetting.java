/**
 * 
 */
package org.tedros.samples.module.sale.setting;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.repository.TRepository;
import org.tedros.extension.model.Address;
import org.tedros.extension.model.AddressMV;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TAutoCompleteEntity;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.form.TSetting;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.model.NaturalPerson;
import org.tedros.samples.module.sale.model.SaleItemMV;
import org.tedros.samples.module.sale.model.SaleMV;
import org.tedros.server.entity.TEntity;
import org.tedros.server.query.TBlock;

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		SaleMV m = super.getModelView();
		if(m.getEntity().isNew())
			m.getDate().setValue(new Date());

		TAutoCompleteEntity cf = super.getControl("customer");
		ChangeListener<TEntity> cChl = (a,o,n) -> {
			if(n!=null) {
				NaturalPerson np = (NaturalPerson) n;
				if(np.getAddress()!=null) {
					Address addr = new Address(np.getAddress());
					m.getDeliveryAddress().setValue(new AddressMV(addr));
				}
			}else
				m.getDeliveryAddress().setValue(null);
		};
		repo.add("cChl",cChl);
		cf.tSelectedItemProperty().addListener(new WeakChangeListener<>(cChl));
		
		TAutoCompleteEntity lpf = super.getControl("legalPerson");
		ChangeListener<TEntity> lpChl = (a,o,n) -> {
			TAutoCompleteEntity sf = super.getControl("seller");
			sf.setText(null);
			sf.gettSelect().getConditions().forEach(c->{
				TBlock b = (TBlock) c;
				if(b.getCondition().getField().equals("legalPerson"))
					b.getCondition().setValue(n);
			});
		};
		repo.add("lpChl", lpChl);
		lpf.tSelectedItemProperty().addListener(new WeakChangeListener<>(lpChl));
		
		TComboBoxField ccf = super.getControl("costCenter");
		ChangeListener ccChl = (a,o,n) -> {
			TAutoCompleteEntity sf = super.getControl("seller");
			sf.setText(null);
			sf.gettSelect().getConditions().forEach(c->{
				TBlock b = (TBlock) c;
				if(b.getCondition().getField().equals("costCenter")) {
					TEntity e = n instanceof TEntityModelView 
							? (TEntity) ((TEntityModelView) n).getEntity() 
									: n instanceof TEntity 
									? (TEntity) n
											: null;
					b.getCondition().setValue(e);
				}
			});
		};
		repo.add("ccChl", ccChl);
		ccf.valueProperty().addListener(new WeakChangeListener<>(ccChl));
		
		
		//Getting the items property from model view
		ITObservableList<SaleItemMV> items = m.getItems();
		ChangeListener<Number> itemsChl = (a,o,n)->{
			calcTotal(items);
		};
		repo.add("itemsChl", itemsChl);
		// Listen for any changes made in any SaleItemMV instances on the list
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
