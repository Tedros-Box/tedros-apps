/**
 * 
 */
package org.tedros.stock.module.inventory.setting;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TAutoCompleteEntity;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.form.TSetting;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.server.entity.TEntity;
import org.tedros.server.query.TBlock;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

/**
 * @author Davis Gordon
 *
 */
public class ResponsableSetting extends TSetting {

	private TRepository repo;
	
	/**
	 * @param descriptor
	 */
	public ResponsableSetting(ITComponentDescriptor descriptor) {
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
			
		TAutoCompleteEntity lpf = super.getControl("legalPerson");
		ChangeListener<TEntity> lpChl = (a,o,n) -> {
			TAutoCompleteEntity sf = super.getControl("responsable");
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
			TAutoCompleteEntity sf = super.getControl("responsable");
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
		
		
	}


}
